package com.example.beerservice.app.screens.main.tabs.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Location
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.model.search.SearchRepository
import com.example.beerservice.app.model.search.entities.SearchData
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.BeersAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val placeRepository: PlacesRepository,
    accountsRepository: AccountsRepository
) : BaseViewModel(accountsRepository), BeersAdapter.BeerSearchListListener,
    PlacePagingAdapter.Listener {

    private val _searchData = MutableLiveData<SearchData>()
    val searchData = _searchData.share()

    private var _onNavigateToBeerDetails = MutableLiveEvent<Int>()
    val onNavigateToBeerDetails = _onNavigateToBeerDetails.share()

    private var _onNavigateToCreateFeedback = MutableLiveEvent<Int>()
    val onNavigateToCreateFeedback = _onNavigateToCreateFeedback.share()

    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()

    private var _onNavigateToPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToPlaceDetails = _onNavigateToPlaceDetails.share()


    private var _onNavigateToMap = MutableLiveEvent<Location>()
    val onNavigateToMap = _onNavigateToMap.share()

    private var searchBy = MutableLiveData("")

    fun getSearchData() {
        viewModelScope.launch {
            val user = accountsRepository.doGetProfile()
            val data: SearchData = searchRepository.getSearchData(user.userId!!, searchBy.value!!)
            _searchData.value = data
        }
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    override fun onNavigateToBeerDetails(beerId: Int) {
        _onNavigateToBeerDetails.publishEvent(beerId)
    }

    override fun onNavigateToCreateFeedback(beerId: Int) {
        _onNavigateToCreateFeedback.publishEvent(beerId)
    }

    override fun onNavigateToPlaceDetails(placeId: Int) {
        _onNavigateToPlaceDetails.publishEvent(placeId)
    }

    override fun onNavigateToMap(geoLat: Double?, geoLon: Double) {
        _onNavigateToMap.publishEvent(Location(geoLat, geoLon))
    }


    override fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val user = accountsRepository.doGetProfile()
            try {
                if (!isFavorite) {
                    addFavorite(PlaceIdUserId(placeId, user.userId!!))
                }
                if (isFavorite) {
                    removeFavorite(PlaceIdUserId(placeId, user.userId!!))
                }
            } catch (e: java.lang.Exception) {
                logError(e)
            }
            _onToggleFavoriteEvent.publishEvent(true)
        }

    }


    private suspend fun addFavorite(placeIdUserId: PlaceIdUserId) {
        placeRepository.setFavorite(placeIdUserId)

    }

    private suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placeRepository.removeFavorite(placeIdUserId)

    }

}