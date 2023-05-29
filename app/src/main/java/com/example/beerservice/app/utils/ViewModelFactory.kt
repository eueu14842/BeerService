package com.example.beerservice.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.beerservice.app.screens.main.auth.SignInViewModel
import com.example.beerservice.app.screens.main.tabs.home.HomeViewModel
import com.example.beerservice.app.screens.main.tabs.home.beers.BeerViewModel
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryDetailsViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListViewModel
import com.example.beerservice.app.screens.main.tabs.home.search.SearchViewModel
import com.example.beerservice.app.screens.main.tabs.places.PlaceDetailsViewModel
import com.example.beerservice.app.screens.main.tabs.places.tabs.PlaceViewModel
import com.example.beerservice.app.screens.main.tabs.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            BreweryListViewModel::class.java -> BreweryListViewModel() as T
            SignInViewModel::class.java -> SignInViewModel() as T
            HomeViewModel::class.java -> HomeViewModel() as T
            BreweryDetailsViewModel::class.java -> BreweryDetailsViewModel() as T
            BeersListViewModel::class.java -> BeersListViewModel() as T
            ProfileViewModel::class.java -> ProfileViewModel() as T
            PlaceViewModel::class.java -> PlaceViewModel() as T
            BeerViewModel::class.java -> BeerViewModel() as T
            PlaceDetailsViewModel::class.java -> PlaceDetailsViewModel() as T
            SearchViewModel::class.java -> SearchViewModel() as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}