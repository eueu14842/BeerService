package com.example.beerservice.app.screens.main.tabs.home.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.FeedbackRepository
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Location
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    val beersRepository: BeersRepository,
    val feedbackRepository: FeedbackRepository,
    val placesRepository: PlacesRepository,
    accountsRepository: AccountsRepository
) : BaseViewModel(accountsRepository) {

    private val _beer = MutableLiveData<ResultState<Beer>>()
    val beer = _beer.share()

    var feedback: Flow<PagingData<FeedbackBeer>>? = null

    private var beerId = MutableLiveData(0)

    private var _onNavigateToMap = MutableLiveEvent<Location>()
    val onNavigateToMap = _onNavigateToMap.share()


    fun getBeerById(id: Int) {
        viewModelScope.launch {
            val result = beersRepository.getBeerById(id)
            if (result == null) _beer.value = ErrorResult(IllegalStateException())
            else _beer.value = Pending()
            _beer.value = Success(result)
        }
    }

    fun getFeedbackByBeerId() {
        feedback = beerId.asFlow()
            .debounce(400)
            .flatMapLatest {
                feedbackRepository.getPagedFeedbackByBeerId(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setBeerId(value: Int) {
        if (this.beerId.value == value) return
        this.beerId.value = value
    }

    fun onNavigateToMap(geoLat: Double?, geoLon: Double) {
        _onNavigateToMap.publishEvent(Location(geoLat, geoLon))
    }

    fun getPlaceByBeerId(beerId: Int) {
        viewModelScope.launch {
            placesRepository.getPagedPlacesByBeerId(beerId)
        }
    }

}