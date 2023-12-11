package com.example.beerservice.app.screens.main.tabs.home.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Location
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class PlacePagedViewModel (
    private val placeRepository: PlacesRepository = Singletons.placesRepository,
) : BaseViewModel(), PlacePagingAdapter.Listener{


    var placesFlow: Flow<PagingData<Place>>
    private var searchBy = MutableLiveData("")

    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()

    private var _onNavigateToPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToPlaceDetails = _onNavigateToPlaceDetails.share()

    private var _onNavigateToMap = MutableLiveEvent<Location>()
    val onNavigateToMap = _onNavigateToMap.share()

    init {
        placesFlow = searchBy.asFlow().debounce(400).flatMapLatest {
            placeRepository.getPagedPlaces()
        }.cachedIn(viewModelScope)
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
            placesFlow = placeRepository.getPagedPlaces()
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