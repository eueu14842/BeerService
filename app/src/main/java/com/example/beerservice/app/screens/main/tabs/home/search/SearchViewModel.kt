package com.example.beerservice.app.screens.main.tabs.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Singletons
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

    fun getSearchData(searchBy: String) {
        viewModelScope.launch {
            val data: SearchData = searchRepository.getSearchData(searchBy)
            _searchData.value = data
        }

    }
}