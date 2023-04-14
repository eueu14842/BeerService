package com.example.beerservice.sources.beers

import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.sources.beers.entities.GetBeerResponseEntity
import com.example.beerservice.sources.brewery.entities.GetBreweryResponseEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BeerApi {
    @POST("beer/createBeer")
    suspend fun createBeer()

    @GET("beer/beer")
    suspend fun getBeer(): GetBeerResponseEntity

    @GET("/beer/list")
    suspend fun getBeerList(): List<GetBeerResponseEntity>

    @GET("beer/beerListByBreweryId")
    suspend fun getBeerListByBreweryId(@Query("id")id: Int)

    @GET("beer/adblock")
    suspend fun getBeerAdblockList(): List<GetBeerResponseEntity>

}