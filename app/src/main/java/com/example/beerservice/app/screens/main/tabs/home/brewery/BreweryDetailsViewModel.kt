package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class BreweryDetailsViewModel(
    val breweryRepository: BreweryRepository = Singletons.breweryRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultNet<Brewery>>()
    val brewery = _brewery.share()

    fun getBrewery(id: Int) {
        viewModelScope.launch {
            val result = breweryRepository.getBreweryById(id)
            if (result == null) _brewery.value = ErrorResult(IllegalStateException("Opps"))
            _brewery.value = Pending()
            _brewery.value = Success(result)
        }


    }

}