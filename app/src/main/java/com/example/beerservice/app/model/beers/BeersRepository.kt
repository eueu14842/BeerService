package com.example.beerservice.app.model.beers

import com.example.beerservice.app.model.beers.entities.Beer

class BeersRepository(
    val beersSource: BeersSource,
    ) {

    suspend fun getBeerById(id: Int) {
        beersSource.getBeerById(id)
    }

    suspend fun getBeerList(): List<Beer> {
        return beersSource.getBeerList()
    }
    suspend fun getBeerAdblockList(): List<Beer> {
        return beersSource.getBeerAdblockList()
    }
}