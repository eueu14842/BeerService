package com.example.beerservice.app.screens.main.tabs.places.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceListFragment
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceMapFragment

class PlaceCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) PlaceMapFragment()
        else PlaceListFragment()
    }
}