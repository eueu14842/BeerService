package com.example.beerservice.app.screens.main.tabs.home.places.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PlaceViewModel(
    private val placeRepository: PlacesRepository = Singletons.placesRepository,
) : BaseViewModel() {

    var placesFlow: Flow<PagingData<Place>>
    private var searchBy = MutableLiveData("")


    private var _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    private var _location = MutableLiveData(Location())
    var location = _location.share()

    init {
        placesFlow = searchBy.asFlow().debounce(400).flatMapLatest {
            placeRepository.getPagedPlaces()
        }.cachedIn(viewModelScope)
    }

    fun getPlaces(lat: Double, lon: Double, rad: Double) {
        viewModelScope.launch {
            val places = placeRepository.getPlacesList(lat, lon, rad)
            if (places.isEmpty()) _place.value = ErrorResult(java.lang.IllegalStateException(""))
            else _place.value = Pending()
            _place.value = Success(places)
        }

    }

    fun setPlacesLocation(lat: Double, lon: Double) {
        this._location.value = Location()
    }

    data class Location(
        val lat: Double = 43.592918,
        val lon: Double = 39.728160
    )
}

