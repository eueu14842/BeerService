package com.example.beerservice.sources.beers

import retrofit2.http.GET
import retrofit2.http.POST

interface BeerApi {
    @POST("beer/createBeer")
    fun createBeer()

    @GET("beer/beer")
    fun getBeer()

    @GET("beer/beerList")
    fun getBeerList()

    @GET("beer/beerListByBreweryId")
    fun getBeerListByBreweryId()
}