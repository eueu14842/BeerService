package com.example.beerservice.app.screens.main.tabs.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Location
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlacePagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    accountsRepository: AccountsRepository
) : BaseViewModel(accountsRepository), PlacePagingAdapter.Listener {


    private var _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    private var _onNavigateToPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToPlaceDetails = _onNavigateToPlaceDetails.share()


    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()

    private var _onNavigateToMapEvent = MutableLiveEvent<Location>()
    var onNavigateToMapEvent = _onNavigateToMapEvent.share()

    fun getFavorite() {
        viewModelScope.launch {
            val profile = accountsRepository.doGetProfile()
            val places = accountsRepository.getFavoritePlaces(profile.userId!!)
            if (places.isEmpty()) _place.value = ErrorResult(IllegalStateException(""))
            else _place.value = Pending()
            _place.value = Success(places)
        }
    }

    override fun onNavigateToPlaceDetails(placeId: Int) {
        _onNavigateToPlaceDetails.publishEvent(placeId)
    }

    override fun onNavigateToMap(geoLat: Double?, geoLon: Double) {
        _onNavigateToMapEvent.publishEvent(Location(geoLat, geoLon))
    }


    override fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val user = accountsRepository.doGetProfile()
            removeFavorite(PlaceIdUserId(placeId, user.userId!!))
            getFavorite()
        }
    }

    private suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placesRepository.removeFavorite(placeIdUserId)

    }

}