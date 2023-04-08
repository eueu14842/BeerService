package com.example.beerservice.app.screens.main.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.ResultNet
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

    private val _brewery = MutableLiveData<ResultNet<List<Brewery>>>()
    val brewery = _brewery.share()

    private val _beer = MutableLiveData<ResultNet<List<Beer>>>()
    val beer = _beer.share()

    private val _place = MutableLiveData<ResultNet<List<Place>>>()
    val place = _place.share()

    init {
        viewModelScope.launch {
            _brewery.value = Success(breweryRepository.getBreweryAdblockList())
        }
        viewModelScope.launch {
            _beer.value = Success(beerRepository.getBeerAdblockList())
        }

        viewModelScope.launch {
            _place.value = Success(placeRepository.getPlacesAdblockList())
        }


    }
}