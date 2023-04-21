package com.example.beerservice.app.model.brewery

import androidx.paging.PagingData
import com.example.beerservice.app.model.brewery.entities.Brewery
import kotlinx.coroutines.flow.Flow

interface BrewerySource {
    suspend fun getBreweryList(): List<Brewery>
    suspend fun getBreweryAdblockList(): List<Brewery>
    suspend fun getBreweryProfileById(id: Int): Brewery

    suspend fun getPagedBrewery(
        pageSize: Int,
        offset: Int,
        searchBy: String
    ): List<Brewery>

}