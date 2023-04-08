package com.example.beerservice.sources.beers

import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.delay

class RetrofitBeerSource(val config: RetrofitConfig) : BaseRetrofitSource(config), BeersSource {

    private val beerApi = config.retrofit.create(BeerApi::class.java)

    override suspend fun getBeer(): Beer = wrapRetrofitExceptions {
        beerApi.getBeer().toBeer()
    }

    override suspend fun getBeerList(): List<Beer> = wrapRetrofitExceptions {
        beerApi.getBeerList().map { it.toBeer() }
    }

    override suspend fun createBeer(beer: Beer) = wrapRetrofitExceptions{
        beerApi.createBeer()
    }

    override suspend fun getBeerListByBreweryId(breweryId: Int) =wrapRetrofitExceptions {
        beerApi.getBeerListByBreweryId()
    }

    override suspend fun getBeerAdblockList(): List<Beer>  =wrapRetrofitExceptions{
        beerApi.getBeerAdblockList().map { it.toBeer() }
    }
}