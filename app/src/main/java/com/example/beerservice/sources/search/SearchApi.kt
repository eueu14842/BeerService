package com.example.beerservice.sources.search

import com.example.beerservice.sources.search.entities.GetSearchResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/beer")
    suspend fun getSearch(@Query("name") searchBy: String): GetSearchResponseEntity
}