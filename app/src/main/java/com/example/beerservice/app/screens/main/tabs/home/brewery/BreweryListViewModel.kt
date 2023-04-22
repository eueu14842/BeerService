package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class BreweryListViewModel(
    breweryRepository: BreweryRepository = Singletons.breweryRepository
) : BaseViewModel() {

    private val _brewery = MutableLiveData<ResultState<List<Brewery>>>()
    val brewery = _brewery.share()

    val breweriesFlow: Flow<PagingData<Brewery>>
    private var searchBy = MutableLiveData("")


    init {
        breweriesFlow = searchBy.asFlow()
            .debounce(400)
            .flatMapLatest {
                breweryRepository.getPagedBrewery(it)
            }
            .cachedIn(viewModelScope)
    }

    //не обрабатываю
    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }


    /*  init {
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
  */
}