package com.example.beerservice.app.screens.main.tabs.home.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.FeedbackRepository
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class BeerViewModel(
    val beersRepository: BeersRepository = Singletons.beerRepository,
    val feedbackRepository: FeedbackRepository = Singletons.feedbackRepository
) : BaseViewModel() {

    private val _beer = MutableLiveData<ResultState<Beer>>()
    val beer = _beer.share()

    var feedback: Flow<PagingData<FeedbackBeer>>? = null

    private var beerId = MutableLiveData(0)

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
                feedbackRepository.getPagedFeedbackById(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setBeerId(value: Int) {
        if (this.beerId.value == value) return
        this.beerId.value = value
    }


}