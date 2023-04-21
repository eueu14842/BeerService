package com.example.beerservice.sources.brewery

import androidx.paging.PagingData
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.flow.Flow

class RetrofitBrewerySource(config: RetrofitConfig) : BaseRetrofitSource(config), BrewerySource {

    private val breweryApi = config.retrofit.create(BreweryApi::class.java)

    override suspend fun getBreweryList(): List<Brewery> = wrapRetrofitExceptions {
        breweryApi.getBreweryList().map { it.toBrewery() }
    }

    override suspend fun getBreweryAdblockList(): List<Brewery> = wrapRetrofitExceptions {
        breweryApi.getBreweryAdblockList().map { it.toBrewery() }
    }

    override suspend fun getBreweryProfileById(id: Int): Brewery = wrapRetrofitExceptions {
        breweryApi.getBreweryById(id).toBrewery()
    }

    override suspend fun getPagedBrewery(
        pageSize: Int,
        offset: Int,
        searchBy: String
    ): List<Brewery> = wrapRetrofitExceptions {
        breweryApi.getPagedBrewery(pageSize, offset, searchBy).map { it.toBrewery() }
    }


}

