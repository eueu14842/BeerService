package com.example.beerservice.sources.brewery

import com.example.beerservice.app.model.ResultNet
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class RetrofitBrewerySource(config: RetrofitConfig) : BaseRetrofitSource(config), BrewerySource {

    private val breweryApi = config.retrofit.create(BreweryApi::class.java)

    override suspend fun getBreweryList(): List<Brewery> = wrapRetrofitExceptions {
        delay(1000)
        breweryApi.getBreweryList().map { it.toBrewery() }
    }
}

