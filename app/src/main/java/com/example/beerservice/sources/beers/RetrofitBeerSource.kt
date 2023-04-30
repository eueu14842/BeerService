package com.example.beerservice.sources.beers

import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.delay

class RetrofitBeerSource(config: RetrofitConfig) : BaseRetrofitSource(config), BeersSource {

    private val beerApi = config.retrofit.create(BeerApi::class.java)

    override suspend fun getBeerById(id: Int): Beer = wrapRetrofitExceptions {
        beerApi.getBeer(id).toBeer()
    }

    override suspend fun getBeerList(): List<Beer> = wrapRetrofitExceptions {
        beerApi.getBeerList().map { it.toBeer() }
    }

    override suspend fun createBeer(beer: Beer) = wrapRetrofitExceptions {
        beerApi.createBeer()
    }


    override suspend fun getBeersListByBreweryId(breweryId: Int,limit:Int,offest: Int) = wrapRetrofitExceptions {
        beerApi.getBeerListByBreweryId(breweryId,limit,offest).map { it.toBeer() }
    }

    override suspend fun getBeerListByPlaceId(placeId: Int): List<Beer> =  wrapRetrofitExceptions  {
       beerApi.getBeersByPlaceId(placeId).map { it.toBeer() }
    }

    override suspend fun getBeerAdblockList(): List<Beer> = wrapRetrofitExceptions {
        beerApi.getBeerAdblockList().map { it.toBeer() }
    }

    override suspend fun getPagedBeer(pageSize: Int, offset: Int) = wrapRetrofitExceptions {
        beerApi.getPagedBeers(pageSize, offset).map { it.toBeer() }
    }
}