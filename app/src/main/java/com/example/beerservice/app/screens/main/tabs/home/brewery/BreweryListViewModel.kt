package com.example.beerservice.app.screens.main.tabs.home.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BreweryListViewModel @Inject constructor(
    breweryRepository: BreweryRepository,
    accountsRepository: AccountsRepository,
) : BaseViewModel(accountsRepository) {

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

}