package com.example.beerservice.app.screens.main.tabs.places.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceLocationListFragment
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceMapFragment

class PlaceCollectionAdapter(
    fragment: Fragment,
    private val lon: Double? = null,
    private val lat: Double? = null
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        println("PlaceCollectionAdapter $lon")
        return if (position == 0) PlaceMapFragment.newInstance(lon, lat)
        else PlaceLocationListFragment()
    }
}