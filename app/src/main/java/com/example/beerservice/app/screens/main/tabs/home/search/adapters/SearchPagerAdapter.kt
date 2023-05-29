package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListFragment
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceListFragment

class SearchPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BeersListFragment()
            1 -> return PlaceListFragment()
            2 -> return BeersListFragment()
        }
        return BeersListFragment()
    }
}