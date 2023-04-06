package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.ResultNet
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BreweryListViewModel(
     breweryRepository: BreweryRepository = Singletons.breweryRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultNet<List<Brewery>>>()
    val brewery = _brewery.share()


    init {
        viewModelScope.launch {
            _brewery.value = Success(breweryRepository.getBreweryList())
        }
    }

}