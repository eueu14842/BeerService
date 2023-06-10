package com.example.beerservice.sources.place

import com.example.beerservice.sources.place.entities.GetPlaceResponseEntity
import retrofit2.http.*

interface PlaceApi {
    @POST("place/createPlaceToBuy")
    suspend fun createPlace(@Body body: Body)

    @POST("place/buyThisBeerFromUs")
    suspend fun buyBeerFromPlace(@Body body: Body)

    @GET("place/list/map")
    suspend fun getPlaceList(
        @Query("geoLat") geoLat: Double,
        @Query("geoLon") geoLon: Double,
        @Query("visibleRegion") visibleRegion: Double
    ): List<GetPlaceResponseEntity>

    @GET("place/list")
    suspend fun getPagedPlaces(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<GetPlaceResponseEntity>


    @GET("place/adblock")
    suspend fun getPlaceAdblockList(): List<GetPlaceResponseEntity>

    @GET("place/profile/{id}")
    suspend fun getPlaceById(
        @Path("id") id: Int
    ): GetPlaceResponseEntity
}