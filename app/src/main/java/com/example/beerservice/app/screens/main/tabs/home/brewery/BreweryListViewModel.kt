package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class BreweryListViewModel(
    breweryRepository: BreweryRepository = Singletons.breweryRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultState<List<Brewery>>>()
    val brewery = _brewery.share()

    init {
        viewModelScope.launch {
            val breweries: List<Brewery> = breweryRepository.getBreweryList()
            if (breweries.isEmpty()) {
                _brewery.value =
                    ErrorResult(java.lang.IllegalStateException("Opps"))
            }
            _brewery.value = Pending()
            _brewery.value = Success(breweries)

        }
    }

}