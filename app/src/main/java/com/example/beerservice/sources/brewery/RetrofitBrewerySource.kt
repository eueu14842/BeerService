package com.example.beerservice.sources.brewery

import androidx.paging.PagingData
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import com.example.beerservice.sources.brewery.entities.GetBreweryResponseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBrewerySource @Inject constructor(config: RetrofitConfig) : BaseRetrofitSource(config), BrewerySource {

     val breweryApi = config.retrofit.create(BreweryApi::class.java)

    override suspend fun getBreweryList(): List<Brewery> = wrapRetrofitExceptions {
        breweryApi.getBreweryList(5,0).map { it.toBrewery() }
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
        breweryApi.getPagedBrewery(pageSize, offset).map { it.toBrewery() }
    }


}

