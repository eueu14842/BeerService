package com.example.beerservice.sources.brewery

import com.example.beerservice.sources.brewery.entities.CreateBreweryRequestEntity
import com.example.beerservice.sources.brewery.entities.GetBreweryResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweryApi {

    @POST("/brewery/create")
    suspend fun createBrewery(@Body body: CreateBreweryRequestEntity)

    @GET("brewery/profile/{id}")
    suspend fun getBreweryById(@Path("id") id: Int): GetBreweryResponseEntity

    @GET("brewery/list")
    suspend fun getBreweryList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GetBreweryResponseEntity>

    @GET("brewery/adblock")
    suspend fun getBreweryAdblockList(): List<GetBreweryResponseEntity>

    @GET("brewery/list")
    suspend fun getPagedBrewery(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GetBreweryResponseEntity>
}