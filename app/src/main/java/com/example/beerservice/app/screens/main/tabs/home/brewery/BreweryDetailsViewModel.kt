package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class BreweryDetailsViewModel(
    val breweryRepository: BreweryRepository = Singletons.breweryRepository,
    private val beersRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultState<Brewery>>()
    val brewery = _brewery.share()

    var beersFlow: Flow<PagingData<Beer>>? = null
    private var breweryId = MutableLiveData(0)


    fun getBeersByBreweryId() {
        beersFlow = breweryId.asFlow()
            .debounce(400)
            .flatMapLatest {
                beersRepository.getPagedBeerByBreweryId(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setBreweryId(value: Int) {
        if (this.breweryId.value == value) return
        this.breweryId.value = value
    }

    fun getBrewery(id: Int) {
        viewModelScope.launch {
            val result = breweryRepository.getBreweryById(id)
            if (result == null) _brewery.value = ErrorResult(IllegalStateException("Opps"))
            _brewery.value = Pending()
            _brewery.value = Success(result)
        }
    }

}