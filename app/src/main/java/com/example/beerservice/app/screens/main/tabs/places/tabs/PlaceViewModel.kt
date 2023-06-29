package com.example.beerservice.app.screens.main.tabs.places.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaceViewModel(
    private val placeRepository: PlacesRepository = Singletons.placesRepository,
) : BaseViewModel(), PlacePagingAdapter.Listener {

    var placesFlow: Flow<PagingData<Place>>
    private var searchBy = MutableLiveData("")

    private var _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    private var _location = MutableLiveData(SharedLocation())
    var location = _location.share()

    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()


    private var _onNavigateToMapPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToMapPlaceDetails = _onNavigateToMapPlaceDetails.share()

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
        this._location.value = SharedLocation(lat, lon)
    }

    data class SharedLocation(
        val lat: Double = 0.0, val lon: Double = 0.0, val rad: Double = 1.5
    )

    override fun onNavigateToPlaceDetails(placeId: Int) {
        _onNavigateToMapPlaceDetails.publishEvent(placeId)
    }

    override fun onNavigateToMap() {
    }

    override fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val user = accountsRepository.doGetProfile()
            try {
                if (!isFavorite) { addFavorite(PlaceIdUserId(placeId, user.userId!!)) }
                if (isFavorite) { removeFavorite(PlaceIdUserId(placeId, user.userId!!)) }
            } catch (e: java.lang.Exception) {
                logError(e)
            }
            placesFlow = placeRepository.getPagedPlaces().cachedIn(viewModelScope)
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

