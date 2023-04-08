package com.example.beerservice.app.model.beers

import com.example.beerservice.app.model.beers.entities.Beer

interface BeersSource {
    suspend fun getBeer(): Beer
    suspend fun getBeerList(): List<Beer>
    suspend fun createBeer(beer: Beer)
    suspend fun getBeerListByBreweryId(breweryId: Int)
    suspend fun getBeerAdblockList(): List<Beer>
}