package com.example.beerservice.app.screens.main.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class HomeViewModel(
    breweryRepository: BreweryRepository = Singletons.breweryRepository,
    beerRepository: BeersRepository = Singletons.beerRepository,
    placeRepository: PlacesRepository = Singletons.placesRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultState<List<Brewery>>>()
    val brewery = _brewery.share()

    private val _beer = MutableLiveData<ResultState<List<Beer>>>()
    val beer = _beer.share()

    private val _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    private val _isAvailableScanner = MutableLiveData<ResultState<Boolean>>()
    val isAvailableScanner = _isAvailableScanner.share()

    init {
        viewModelScope.launch {
            _isAvailableScanner.value = Success(false)
        }
        viewModelScope.launch {
            _brewery.value = Pending()
            _brewery.value = Success(breweryRepository.getBreweryAdblockList())
        }
        viewModelScope.launch {
            _beer.value = Pending()
            _beer.value = Success(beerRepository.getBeerAdblockList())
        }

        viewModelScope.launch {
            _place.value = Pending()
            _place.value = Success(placeRepository.getPlacesAdblockList())
        }
    }

    fun setupScanner(boolean: Boolean) {
        _isAvailableScanner.value = Success(boolean)
    }
}