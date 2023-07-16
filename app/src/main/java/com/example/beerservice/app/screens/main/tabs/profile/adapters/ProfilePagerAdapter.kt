package com.example.beerservice.app.screens.main.tabs.profile.adapters

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListFragment
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListFragment
import com.example.beerservice.app.screens.main.tabs.home.places.PlaceListFragment
import com.example.beerservice.app.screens.main.tabs.home.search.SearchFragmentDirections
import com.example.beerservice.app.screens.main.tabs.home.search.beers.BeerSearchListFragment
import com.example.beerservice.app.screens.main.tabs.home.search.breweries.BrewerySearchListFragment
import com.example.beerservice.app.screens.main.tabs.home.search.places.PlaceSearchListFragment
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceLocationListFragment

class ProfilePagerAdapter(
    val fragment: Fragment,
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }
}