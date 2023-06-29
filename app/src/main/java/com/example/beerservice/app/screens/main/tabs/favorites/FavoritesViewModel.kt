package com.example.beerservice.app.screens.main.tabs.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.Singletons.placesRepository
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.places.adapters.PlaceListAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class FavoritesViewModel(
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    val placesRepository: PlacesRepository = Singletons.placesRepository
) : BaseViewModel(),PlaceListAdapter.Listener {


    private var _place = MutableLiveData<ResultState<List<Place>>>()
    val place = _place.share()

    private var _onNavigateToPlaceDetails = MutableLiveEvent<Int>()
    val onNavigateToPlaceDetails = _onNavigateToPlaceDetails.share()


    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()


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

    override fun onNavigateToMap() {
        TODO("Not yet implemented")
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
//            placesFlow = placeRepository.getPagedPlaces().cachedIn(viewModelScope)
            _onToggleFavoriteEvent.publishEvent(true)
        }

    }
    private suspend fun addFavorite(placeIdUserId: PlaceIdUserId) {
        placesRepository.setFavorite(placeIdUserId)

    }

    private suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placesRepository.removeFavorite(placeIdUserId)

    }

}