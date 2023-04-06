package com.example.beerservice.sources.brewery

import com.example.beerservice.sources.brewery.entities.CreateBreweryRequestEntity
import com.example.beerservice.sources.brewery.entities.GetBreweryResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BreweryApi {

    @POST("brewery/createBrewery")
    fun createBrewery(@Body body: CreateBreweryRequestEntity)

    @GET("brewery/brewery")
    fun getBrewery(): GetBreweryResponseEntity

    @GET("brewery/list")
    suspend fun getBreweryList(): List<GetBreweryResponseEntity>
}