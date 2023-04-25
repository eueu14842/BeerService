package com.example.beerservice.app.screens.main.tabs.home.beers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class BeersListViewModel(
    private val beersRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel() {

    private val _beers = MutableLiveData<ResultState<List<Beer>>>()
    val beers = _beers.share()

    val beersFlow: Flow<PagingData<Beer>>
    private var searchBy = MutableLiveData("")


    init {
        beersFlow = searchBy.asFlow()
            .debounce(400)
            .flatMapLatest {
                beersRepository.getPagedBeer(it)
            }
            .cachedIn(viewModelScope)
    }


/*    init {
        viewModelScope.launch {
            val beers: List<Beer> = beersRepository.getBeerList()
            if (beers.isEmpty()) {
                _beers.value =
                    ErrorResult(IllegalStateException("Opps"))
            }
            _beers.value = Pending()
            _beers.value = Success(beers)
        }
    }*/
}