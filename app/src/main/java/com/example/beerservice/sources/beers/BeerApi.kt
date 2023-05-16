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

    @GET("beer/profile")
    suspend fun getBeer(@Query("id") id: Int): GetBeerResponseEntity

    @GET("/beer/list")
    suspend fun getBeerList(): List<GetBeerResponseEntity>

    @GET("beer/list/brewery")
    suspend fun getBeerListByBreweryId(
        @Query("breweryId") breweryId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<GetBeerResponseEntity>

    @GET("beer/adblock")
    suspend fun getBeerAdblockList(): List<GetBeerResponseEntity>

    @GET("beer/place")
    suspend fun getBeersByPlaceId(
        @Query("id") placeId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<GetBeerResponseEntity>

    @GET("beer/list")
    suspend fun getPagedBeers(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GetBeerResponseEntity>

}