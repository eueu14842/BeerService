package com.example.beerservice.app.model.beers

import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery

interface BeersSource {
    suspend fun getBeerById(id: Int): Beer
    suspend fun getBeerList(): List<Beer>
    suspend fun createBeer(beer: Beer)
    suspend fun getBeerListByBreweryId(breweryId: Int)
    suspend fun getBeerAdblockList(): List<Beer>
    suspend fun getPagedBeer(
        pageSize: Int,
        offset: Int,
    ): List<Beer>

}