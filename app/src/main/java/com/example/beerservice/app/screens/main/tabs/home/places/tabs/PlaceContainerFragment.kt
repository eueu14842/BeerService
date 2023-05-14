package com.example.beerservice.app.screens.main.tabs.home.places.tabs

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.example.beerservice.R
import com.example.beerservice.app.screens.main.tabs.home.places.adapters.PlaceCollectionAdapter
import com.example.beerservice.databinding.FragmentPlacesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PlaceContainerFragment : Fragment(R.layout.fragment_places) {
    lateinit var binding: FragmentPlacesBinding
    private lateinit var placesCollectionAdapter: PlaceCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlacesBinding.bind(view)
        setupViews()
        placesCollectionAdapter = PlaceCollectionAdapter(this)
        viewPager.adapter = placesCollectionAdapter
        prepareMediator()
        onRequestLocationPermissions()

    }

    private fun setupViews() {
        tabLayout = binding.tabLayout
        viewPager = binding.pager
    }

    private fun prepareMediator() {
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