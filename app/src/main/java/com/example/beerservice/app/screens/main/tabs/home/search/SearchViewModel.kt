package com.example.beerservice.app.screens.main.tabs.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.search.SearchRepository
import com.example.beerservice.app.model.search.entities.SearchData
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository = Singletons.searchRepository
) : BaseViewModel() {

    private val _searchData = MutableLiveData<SearchData>()
    val searchData = _searchData.share()

    private var _beers = MutableLiveData<List<Beer>?>()
    val beers = _beers.share()

    private var searchBy = MutableLiveData("")

    fun getSearchData() {
        viewModelScope.launch {
            val data: SearchData = searchRepository.getSearchData(searchBy.value!!)
            _beers.value = data.beer
            _searchData.value = data
        }
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }
}