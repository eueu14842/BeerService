package com.example.beerservice.app.screens.main.tabs.home.places.tabs


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beerservice.R
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlacesMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.cos
import java.net.URL
import kotlin.concurrent.thread
import kotlin.math.pow


class PlaceMapFragment : BaseFragment(R.layout.fragment_places_map), CameraListener {
    lateinit var mapview: MapView
    lateinit var binding: FragmentPlacesMapBinding
    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }

    private var locationManager: android.location.LocationManager? = null

    lateinit var map: Map
    lateinit var mapObjects: MapObjectCollection
    private var point: Point = Point(55.764094, 37.617617)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacesMapBinding.inflate(inflater, container, false)

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
            viewModel.getPlaces(lat, lon, 1.5)
            viewModel.place.observe(viewLifecycleOwner) { result ->
                result.map { places ->
                    places.forEach {
                        createTappableMark(
                            MapObjectPlaceData(
                                id = it.placeId,
                                description = it.description,
                                geoLat = it.geoLat,
                                geoLon = it.geoLon
                            ),
                            // TODO:  
                        )
                    }
                }
            }

        }
    }

    suspend fun getBitmap(string: String?, block: (String?) -> Bitmap): Bitmap {
        return withContext(Dispatchers.IO) {
            // TODO:  
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
        viewModel.setPlacesLocation(lat, lon, rad)
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

    private fun createTappableMark(placeMark: MapObjectPlaceData, bitmap: Bitmap) {
        val placeObject =
            placeMark.geoLat?.let { placeMark.geoLon?.let { it1 -> Point(it, it1) } }
                ?.let { mapObjects.addPlacemark(it) }
        placeObject?.userData = placeMark
        setPlaceMarkIcon(placeObject, bitmap)
        placeObject?.addTapListener(mapPlaceTapListener)
    }

    private fun setPlaceMarkIcon(placeMark: PlacemarkMapObject?, bitmap: Bitmap) {
        placeMark?.setIcon(ImageProvider.fromBitmap(bitmap))
    }

    private val mapPlaceTapListener = MapObjectTapListener { mapObject, point ->
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

    override fun onCameraPositionChanged(
        map: Map,
        position: CameraPosition,
        cameraPosition: CameraUpdateReason,
        finished: Boolean
    ) {
        if (finished) {
            val currentZoom = position.zoom
            val radius: Double = calculateRadiusInKilometers(currentZoom, point.latitude)
            viewModel.getPlaces(
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
        val id: Int?, val description: String?, val geoLat: Double?, val geoLon: Double?
    )
}