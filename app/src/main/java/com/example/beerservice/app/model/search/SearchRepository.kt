package com.example.beerservice.app.model.search

import com.example.beerservice.app.model.search.entities.SearchData

class SearchRepository(
    val searchSource: SearchSource
) {

    suspend fun getSearchData(userId: Int, searchBy: String): SearchData {
        return searchSource.getSearchData(userId, searchBy)
    }
}