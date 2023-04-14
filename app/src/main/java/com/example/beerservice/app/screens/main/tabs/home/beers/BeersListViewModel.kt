package com.example.beerservice.app.screens.main.tabs.home.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class BeersListViewModel(
    private val beersRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel() {

    private val _beers = MutableLiveData<ResultNet<List<Beer>>>()
    val beers = _beers.share()

    init {
        viewModelScope.launch {
            val beers: List<Beer> = beersRepository.getBeerList()
            if (beers.isEmpty()) {
                _beers.value =
                    ErrorResult(IllegalStateException("Opps"))
            }
            _beers.value = Pending()
            _beers.value = Success(beers)
        }
    }
}