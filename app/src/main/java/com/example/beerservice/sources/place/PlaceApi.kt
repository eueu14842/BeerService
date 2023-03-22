package com.example.beerservice.sources.place

import retrofit2.http.Body
import retrofit2.http.POST

interface PlaceApi {
    @POST("place/createPlaceToBuy")
    fun createPlace(@Body body: Body)

    @POST("place/buyThisBeerFromUs")
    fun buyBeerFromPlace(@Body body: Body)
}