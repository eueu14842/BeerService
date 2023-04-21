package com.example.beerservice.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.screens.base.BaseViewModel

import com.example.beerservice.app.screens.main.auth.SignInViewModel
import com.example.beerservice.app.screens.main.tabs.home.HomeViewModel
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListFragment
import com.example.beerservice.app.screens.main.tabs.home.beers.BeersListViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryDetailsFragment
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryDetailsViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListViewModel
import com.example.beerservice.app.screens.main.tabs.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            BreweryListViewModel::class.java ->  BreweryListViewModel() as T
            SignInViewModel::class.java ->  SignInViewModel() as T
            HomeViewModel::class.java ->  HomeViewModel() as T
            BreweryDetailsViewModel::class.java ->  BreweryDetailsViewModel() as T
            BeersListViewModel::class.java -> BeersListViewModel() as T
            ProfileViewModel::class.java -> ProfileViewModel() as T
            else -> {
                throw  IllegalStateException("")
            }
        }
    }
}