package com.example.beerservice.app.screens.main.tabs.home.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.entities.FeedbackBeerCreate
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.tabs.home.beers.adapters.BeerPagingAdapter
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class BeersListViewModel(
    private val beersRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel(),BeerPagingAdapter.BeerListener {

    val beersFlow: Flow<PagingData<Beer>>
    private var searchBy = MutableLiveData("")

    private var _onNavigateToBeerDetails = MutableLiveEvent<Int>()
    val onNavigateToBeerDetails = _onNavigateToBeerDetails.share()


    private var _onNavigateToCreateFeedback = MutableLiveEvent<Int>()
    val onNavigateToCreateFeedback = _onNavigateToCreateFeedback.share()


    init {
        beersFlow = searchBy.asFlow()
            .debounce(400)
            .flatMapLatest {
                beersRepository.getPagedBeer()
            }
            .cachedIn(viewModelScope)
    }

    override fun onNavigateToBeerDetails(beerId: Int) {
        _onNavigateToBeerDetails.publishEvent(beerId)
    }

    override fun onToggleRatingBar() {
        TODO("Not yet implemented")
    }

    override fun onNavigateToCreateFeedback(beerId: Int) {
        TODO("Not yet implemented")
    }


}