package com.example.beerservice.app.screens.main.tabs.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.model.search.SearchRepository
import com.example.beerservice.app.model.search.entities.SearchData
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.home.search.adapters.BeersAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository = Singletons.searchRepository,
    private val placeRepository: PlacesRepository = Singletons.placesRepository
) : BaseViewModel(), BeersAdapter.BeerSearchListListener, PlacePagingAdapter.Listener {

    private val _searchData = MutableLiveData<SearchData>()
    val searchData = _searchData.share()

    private var _beers = MutableLiveData<List<Beer>?>()
    val beers = _beers.share()

    private var _onNavigateToBeerDetails = MutableLiveEvent<Int>()
    val onNavigateToBeerDetails = _onNavigateToBeerDetails.share()

    private var _onNavigateToCreateFeedback = MutableLiveEvent<Int>()
    val onNavigateToCreateFeedback = _onNavigateToCreateFeedback.share()


    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()


    private var _onNavigateToMapPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToMapPlaceDetails = _onNavigateToMapPlaceDetails.share()


    private var searchBy = MutableLiveData("")

    fun getSearchData() {
        viewModelScope.launch {
            val data: SearchData = searchRepository.getSearchData(searchBy.value!!)
            _beers.value = data.beer
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
        _onNavigateToMapPlaceDetails.publishEvent(placeId)
    }

    override fun onNavigateToMap() {
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
//            placesFlow = placeRepository.getPagedPlaces()
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