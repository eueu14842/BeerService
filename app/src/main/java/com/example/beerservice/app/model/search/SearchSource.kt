package com.example.beerservice.app.model.search

import com.example.beerservice.app.model.search.entities.SearchData

interface SearchSource {
    suspend fun getSearchData(userId: Int, searchBy: String): SearchData
}