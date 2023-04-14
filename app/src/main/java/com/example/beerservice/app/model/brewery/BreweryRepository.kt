package com.example.beerservice.app.model.brewery

import com.example.beerservice.app.model.ResultNet
import com.example.beerservice.app.model.brewery.entities.Brewery
import kotlinx.coroutines.flow.Flow

class BreweryRepository(
    val brewerySource: BrewerySource
) {

    suspend fun getBreweryList(): List<Brewery> {
        return brewerySource.getBreweryList()
    }

    suspend fun getBreweryAdblockList(): List<Brewery> {
        return brewerySource.getBreweryAdblockList()
    }

    suspend fun getBreweryById(id: Int): Brewery {
        return brewerySource.getBreweryProfileById(id)
    }
}