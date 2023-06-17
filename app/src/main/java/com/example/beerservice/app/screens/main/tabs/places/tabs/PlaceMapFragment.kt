package com.example.beerservice.app.screens.main.tabs.places.tabs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.beerservice.R
import com.example.beerservice.app.Const
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlacesMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch
import java.lang.Math.cos
import kotlin.math.pow

class PlaceMapFragment : BaseFragment(R.layout.fragment_places_map), CameraListener {

    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }
    lateinit var viewModelPlace: PlaceViewModel
    private var locationManager: android.location.LocationManager? = null
    private var point: Point = Point(55.764094, 37.617617)

    lateinit var binding: FragmentPlacesMapBinding

    lateinit var mapview: MapView
    lateinit var map: Map
    lateinit var mapObjects: MapObjectCollection
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacesMapBinding.inflate(inflater, container, false)

        viewModelPlace = ViewModelProvider(requireActivity())[PlaceViewModel::class.java]
        mapview = binding.mapview
        map = mapview.map
        mapObjects = map.mapObjects.addCollection()


        setupLocationManager()
        getCurrentPosition(getAvailableProvider())
        observePlaces(point.latitude, point.longitude)
        onNavigateToCurrentPosition(point)
        map.addCameraListener(this)

        return binding.root

    }

    private fun observePlaces(lat: Double, lon: Double) {
        lifecycleScope.launch {
            viewModelPlace.getPlaces(lat, lon, 1.5)
            viewModelPlace.place.observe(viewLifecycleOwner) { result ->
                result.map { places ->
                    places.forEach {
                        createTappableMark(
                            MapObjectPlaceData(
                                id = it.placeId,
                                description = it.description,
                                geoLat = it.geoLat,
                                geoLon = it.geoLon,
                            ),
                            it.image
                        )
                    }
                }
            }
        }
    }

    private fun onNavigateToCurrentPosition(point: Point) {
        mapview.map.move(CameraPosition(point, 14F, 0F, 0F))
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCurrentPosition(provider: String) {
        if (checkLocationPermission()) {
            val location: Location? =
                locationManager?.getLastKnownLocation(provider)
            if (location != null) {
                point = Point(location.latitude, location.longitude)
                return
            }
            locationManager?.requestLocationUpdates(
                provider, 0, 0f
            ) { loc -> point = Point(loc.latitude, loc.longitude) }
        } else return
    }


    private fun setLocation(lat: Double, lon: Double, rad: Double) {
        viewModelPlace.setPlacesLocation(lat, lon)
    }


    private fun getAvailableProvider(): String {
        val providers = locationManager!!.getProviders(true)
        for (provider in providers) {
            if (locationManager!!.isProviderEnabled(provider)) {
                return provider
            }
        }
        return android.location.LocationManager.NETWORK_PROVIDER
    }

    private fun setupLocationManager() {
        locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as? android.location.LocationManager
    }

    private fun createTappableMark(placeMark: MapObjectPlaceData, imageUrl: String?) {
        val placeObject =
            placeMark.geoLat?.let { placeMark.geoLon?.let { it1 -> Point(it, it1) } }
                ?.let { mapObjects.addPlacemark(it) }
        placeObject?.userData = placeMark
        setPlaceMarkIcon(placeObject, imageUrl)
        placeObject?.addTapListener(mapPlaceTapListener)
    }

    private fun setPlaceMarkIcon(placeMark: PlacemarkMapObject?, imageUrl: String?) {
        Glide.with(requireContext())
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    bitmap = resource
                    val iconSize = resources.getDimensionPixelSize(R.dimen.marker_icon_size)
                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, false)
                    placeMark?.setIcon(ImageProvider.fromBitmap(scaledBitmap))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
//                    bitmap.recycle()
                }

            })

    }

    private val mapPlaceTapListener = MapObjectTapListener { mapObject, _ ->
        if (mapObject is PlacemarkMapObject) {
            val data = mapObject.userData as MapObjectPlaceData
            val direction =
                PlaceContainerFragmentDirections.actionPlaceFragmentToPlaceDetailsFragment(
                    data.id!!
                )
            findNavController().navigate(direction)
        }
        true
    }


    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop();

        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart();

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCameraPositionChanged(
        map: Map,
        position: CameraPosition,
        cameraPosition: CameraUpdateReason,
        finished: Boolean
    ) {
        if (finished) {
            val currentZoom = position.zoom
            val radius: Double = calculateRadiusInKilometers(currentZoom, point.latitude)
            viewModelPlace.getPlaces(
                point.latitude,
                point.longitude,
                radius * 10
            )
            setLocation(
                point.latitude,
                point.longitude,
                radius * 10
            )
        }
    }

    /**
     * Рассчитываем радиус в километрах при текущем zoom level
     * @param zoom текущий масштаб карты (zoom level)
     * @param latitude текущая широта (в градусах)
     * @return радиус в километрах
     */

    private fun calculateRadiusInKilometers(zoom: Float, latitude: Double): Double {
        // Константа для перевода градусов в радианы
        val degreesToRadiansFactor = Math.PI / 180.0
        // Радиус Земли в километрах
        val earthRadiusInKilometers = 6371.0
        // Угловое разрешение текущего масштаба карты в градусах на пиксель
        val angularResolution = 360.0 / (256.0 * 2.0.pow(zoom.toDouble()))
        // Радиус в километрах с учетом широты
        val radius =
            earthRadiusInKilometers * cos(latitude * degreesToRadiansFactor) * angularResolution / 2.0
        return radius
    }

    class MapObjectPlaceData(
        val id: Int?,
        val description: String?,
        val geoLat: Double?,
        val geoLon: Double?
    )
}