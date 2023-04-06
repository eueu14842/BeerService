package com.example.beerservice.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.brewery.BreweryRepository

import com.example.beerservice.app.screens.main.auth.SignInViewModel
import com.example.beerservice.app.screens.main.tabs.home.HomeViewModel
import com.example.beerservice.app.screens.main.tabs.home.brewery.BreweryListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            BreweryListViewModel::class.java ->  BreweryListViewModel() as T
            SignInViewModel::class.java ->  SignInViewModel() as T
            else -> {
                throw  IllegalStateException("")
            }
        }
    }
}