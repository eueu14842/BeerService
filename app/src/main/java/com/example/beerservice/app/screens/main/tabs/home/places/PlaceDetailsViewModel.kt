package com.example.beerservice.app.screens.main.tabs.home.places

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.R
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
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
class PlaceDetailsViewModel @Inject constructor(
    val placesRepository: PlacesRepository ,
    val beersRepository: BeersRepository ,
    accountsRepository: AccountsRepository
) : BaseViewModel(accountsRepository), View.OnClickListener, BeerPagingAdapter.BeerListListener {


    private val _place = MutableLiveData<ResultState<Place>>()
    val place = _place.share()

    var beersFlow: Flow<PagingData<Beer>>? = null
    private var breweryId = MutableLiveData(0)


    private var _onToggleFavoriteEvent = MutableLiveEvent<Boolean>()
    val onToggleFavoriteEvent = _onToggleFavoriteEvent.share()


    private var _onNavigateToBeerDetails = MutableLiveEvent<Int>()
    val onNavigateToBeerDetails = _onNavigateToBeerDetails.share()

    private var _onNavigateToCreateFeedback = MutableLiveEvent<Int>()
    val onNavigateToCreateFeedback = _onNavigateToCreateFeedback.share()

    private var _onNavigateToMap = MutableLiveEvent<Location>()
    val onNavigateToMap = _onNavigateToMap.share()


    override fun onClick(v: View?) {
        val result = place.value
        result?.map { place ->
            when (v?.id) {
                R.id.heartImageView -> {
                    onToggleFavoriteFlag(
                        place.placeId!!,
                        place.setAvailabilityOfSpaceForTheUser!!
                    )
                }
                R.id.textViewShowPlaceOnMap -> onNavigateToMap(place.geoLat, place.geoLon!!)
            }
        }

    }

    fun getPlace(id: Int) {
        viewModelScope.launch {
            val user = accountsRepository.doGetProfile()
            val result = placesRepository.getPlaceById(id, user.userId!!)
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

    fun onNavigateToMap(geoLat: Double?, geoLon: Double) {
        _onNavigateToMap.publishEvent(Location(geoLat, geoLon))
    }

    fun onToggleFavoriteFlag(placeId: Int, isFavorite: Boolean) {
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
        placesRepository.setFavorite(placeIdUserId)

    }

    private suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placesRepository.removeFavorite(placeIdUserId)

    }

    override fun onNavigateToBeerDetails(beerId: Int) {
        _onNavigateToBeerDetails.publishEvent(beerId)
    }


    override fun onNavigateToCreateFeedback(beerId: Int) {
        _onNavigateToCreateFeedback.publishEvent(beerId)
    }


    data class Location(
        val geoLat: Double? = null,
        val geoLon: Double? = null,
    )

}

