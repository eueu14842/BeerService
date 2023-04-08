package com.example.beerservice.sources.place

import com.example.beerservice.sources.place.entities.PlaceRequestEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceApi {
    @POST("place/createPlaceToBuy")
    suspend fun createPlace(@Body body: Body)

    @POST("place/buyThisBeerFromUs")
    suspend fun buyBeerFromPlace(@Body body: Body)

    @GET("/place/list")
    suspend fun getPlaceList(): List<PlaceRequestEntity>

    @GET("place/adblock")
    suspend fun getPlaceAdblockList(): List<PlaceRequestEntity>
}