package com.example.beerservice.app.screens.main.tabs.home.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.beerservice.R
import com.example.beerservice.app.Const.MAPKIT_API_KEY
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.databinding.FragmentPlacesMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class PlaceMapFragment : BaseFragment(R.layout.fragment_places_map) {
    lateinit var mapview: MapView
    lateinit var binding: FragmentPlacesMapBinding
    override val viewModel: PlaceMapViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        MapKitFactory.initialize(requireActivity())

        binding = FragmentPlacesMapBinding.inflate(inflater, container, false)

        mapview = binding.mapview
        mapview.map.move(
            CameraPosition(Point(56.363618, 41.311222), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        return binding.root

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


}