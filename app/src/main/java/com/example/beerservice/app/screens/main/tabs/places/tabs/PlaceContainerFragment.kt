package com.example.beerservice.app.screens.main.tabs.places.tabs

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.beerservice.R
import com.example.beerservice.app.Const.LATITUDE
import com.example.beerservice.app.Const.LONGITUDE
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceCollectionAdapter
import com.example.beerservice.databinding.FragmentPlaceContainerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PlaceContainerFragment : Fragment(R.layout.fragment_place_container) {
    lateinit var binding: FragmentPlaceContainerBinding
    private lateinit var placesCollectionAdapter: PlaceCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceContainerBinding.bind(view)

        setupViews()

        val lon = arguments?.getDouble(LONGITUDE)
        val lat = arguments?.getDouble(LATITUDE)
        placesCollectionAdapter = if (lon == null) PlaceCollectionAdapter(this)
        else PlaceCollectionAdapter(this, lon, lat)
        viewPager.adapter = placesCollectionAdapter
        setupMediator()
        onRequestLocationPermissions()


    }

    private fun setupViews() {
        tabLayout = binding.tabLayout
        viewPager = binding.pager
    }

    private fun setupMediator() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.isUserInputEnabled = false
            if (position == 0) {
                tab.setText(R.string.place_map)
            } else tab.setText(R.string.place_list)
        }.attach()
    }

    private fun onRequestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        }
    }

}