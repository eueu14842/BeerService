package com.example.beerservice.app.screens.main.tabs.home.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(
    private val placesRepository: PlacesRepository = Singletons.placesRepository,
    private val beersRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel() {

    private val _place = MutableLiveData<ResultState<Place>>()
    val place = _place.share()

    var beersFlow: Flow<PagingData<Beer>>? = null
    private var breweryId = MutableLiveData(0)


    fun getPlace(id: Int) {
        viewModelScope.launch {
            val result = placesRepository.getPlaceById(id)
            _place.value = Pending()
            _place.value = Success(result)
        }
    }

    fun getBeersByPlaceId() {
        beersFlow = breweryId.asFlow()
            .debounce(400)
            .flatMapLatest {
                beersRepository.getPagedBeerByPlaceId(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setPlaceId(value: Int) {
        if (this.breweryId.value == value) return
        this.breweryId.value = value
    }


}