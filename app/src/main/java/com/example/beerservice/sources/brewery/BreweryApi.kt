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

    @GET("brewery/profile")
    suspend fun getBreweryById(@Query("id") id: Int): GetBreweryResponseEntity

    @GET("brewery/list")
    suspend fun getBreweryList(): List<GetBreweryResponseEntity>

    @GET("brewery/adblock")
    suspend fun getBreweryAdblockList(): List<GetBreweryResponseEntity>
}