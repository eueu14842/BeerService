package com.example.beerservice.app.screens.main.tabs.home.places.tabs


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.coroutines.launch
import java.lang.Math.cos
import kotlin.math.pow


class PlaceMapFragment : BaseFragment(R.layout.fragment_places_map), CameraListener {
    lateinit var mapview: MapView
    lateinit var binding: FragmentPlacesMapBinding
    override val viewModel: PlaceViewModel by viewModels { ViewModelFactory() }

    private var locationManager: android.location.LocationManager? = null

    lateinit var map: Map
    lateinit var mapObjects: MapObjectCollection
    lateinit var point: Point

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

        point = getCurrentPosition(getAvailableProvider())
        observePlaces(point.latitude, point.longitude)
        onNavigateToCurrentPosition(point)
        map.addCameraListener(this)

        return binding.root

    }

    // 43.592918, 39.728160

    private fun observePlaces(lat: Double, lon: Double) {
        lifecycleScope.launch {
            viewModel.getPlaces(lat, lon, 1.5)
            viewModel.place.observe(viewLifecycleOwner) { result ->
                result.map { place ->
                    place.forEach {
                        createTappableMark(
                            MapObjectPlaceData(
                                id = it.placeId,
                                description = it.description,
                                geoLat = it.geoLat,
                                geoLon = it.geoLon
                            )
                        )
                    }
                    setLocation(lat, lon)
                }
            }
        }
    }


    private fun onNavigateToCurrentPosition(point: Point) {
        mapview.map.move(CameraPosition(point, 14F, 0F, 0F))
    }

    private fun getCurrentPosition(provider: String): Point {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val location: Location =
                locationManager?.getLastKnownLocation(provider) ?: return Point(
                    55.755841, 37.617563
                )
            Point(
                location.latitude, location.longitude
            )
        } else Point(55.755841, 37.617563)

    }


    private fun setLocation(lat: Double, lon: Double) {
        viewModel.setPlacesLocation(lat, lon)
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

    private fun createTappableMark(placeMark: MapObjectPlaceData) {
        val placeObject = placeMark.geoLat?.let { placeMark.geoLon?.let { it1 -> Point(it, it1) } }
            ?.let { mapObjects.addPlacemark(it) }
        placeObject?.userData = placeMark
        setPlaceMarkIcon(placeObject)
        placeObject?.addTapListener(mapPlaceTapListener)
    }

    private fun setPlaceMarkIcon(placeMark: PlacemarkMapObject?) {
        placeMark?.setIcon(
            ImageProvider.fromResource(
                requireContext(),
                R.drawable.beer_icon_small
            )
        )
    }

    private val mapPlaceTapListener = object : MapObjectTapListener {
        override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
            if (mapObject is PlacemarkMapObject) {
                val data = mapObject.userData as MapObjectPlaceData
                val direction =
                    PlaceContainerFragmentDirections.actionPlaceFragmentToPlaceDetailsFragment(data.id!!)
                findNavController().navigate(direction)
            }
            return true
        }
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
            println(radius)
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