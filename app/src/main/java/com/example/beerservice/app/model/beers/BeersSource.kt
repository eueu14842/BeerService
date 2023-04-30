package com.example.beerservice.app.model.beers

import com.example.beerservice.app.model.beers.entities.Beer

interface BeersSource {
    suspend fun getBeerById(id: Int): Beer
    suspend fun getBeerList(): List<Beer>
    suspend fun createBeer(beer: Beer)
    suspend fun getBeersListByBreweryId(breweryId: Int, limit: Int, offset: Int): List<Beer>
    suspend fun getBeerListByPlaceId(placeId: Int): List<Beer>
    suspend fun getBeerAdblockList(): List<Beer>
    suspend fun getPagedBeer(
        pageSize: Int,
        offset: Int,
    ): List<Beer>

}