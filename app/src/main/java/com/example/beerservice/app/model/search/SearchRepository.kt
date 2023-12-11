package com.example.beerservice.app.model.search

import com.example.beerservice.app.model.search.entities.SearchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    val searchSource: SearchSource
) {

    suspend fun getSearchData(userId: Int, searchBy: String): SearchData {
        return searchSource.getSearchData(userId, searchBy)
    }
}