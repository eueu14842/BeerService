package com.example.beerservice.app.screens.main.tabs.home.search.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerservice.app.screens.main.tabs.home.search.beers.BeerSearchListFragment
import com.example.beerservice.app.screens.main.tabs.home.search.breweries.BrewerySearchListFragment
import com.example.beerservice.app.screens.main.tabs.home.search.places.PlaceSearchListFragment

class SearchPagerAdapter(
    val fragment: Fragment,
    val searchBy: String
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BeerSearchListFragment.newInstance(searchBy)
            1 -> return PlaceSearchListFragment.newInstance(searchBy)
            2 -> return BrewerySearchListFragment.newInstance(searchBy)
        }
        return BeerSearchListFragment()
    }
}