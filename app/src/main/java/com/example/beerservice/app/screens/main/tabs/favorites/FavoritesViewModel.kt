package com.example.beerservice.app.screens.main.tabs.favorites

import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class FavoritesViewModel(
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    placesRepository: PlacesRepository = Singletons.placesRepository
) : BaseViewModel() {

    private val _initialEditEvent = MutableLiveEvent<User>()
    val initialEditEvent = _initialEditEvent.share()

    init {
        viewModelScope.launch {
            val result = accountsRepository.doGetProfile()
            accountsRepository.getFavoritePlaces(result.userId!!)
        }
    }



}