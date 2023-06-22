package com.example.beerservice.app.screens.main.tabs.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class FavoritesViewModel(
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    placesRepository: PlacesRepository = Singletons.placesRepository
) : BaseViewModel() {


    private var _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    fun getFavorite() {
        viewModelScope.launch {
            val profile = accountsRepository.doGetProfile()
            val places = accountsRepository.getFavoritePlaces(profile.userId!!)
            if (places.isEmpty()) _place.value = ErrorResult(IllegalStateException(""))
            else _place.value = Pending()
            _place.value = Success(places)
        }
    }

}