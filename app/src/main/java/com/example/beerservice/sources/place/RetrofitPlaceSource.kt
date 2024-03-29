package com.example.beerservice.sources.place

import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig

class RetrofitPlaceSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), PlaceSource {

    private val placeApi = retrofit.create(PlaceApi::class.java)

    override suspend fun getPlaceList(
        geoLat: Double,
        geoLon: Double,
        visibleRegion: Double
    ): List<Place> = wrapRetrofitExceptions {
        placeApi.getPlaceList(geoLat, geoLon, visibleRegion).map { it.toPlace() }
    }

    override suspend fun getPlacesAdblockList() = wrapRetrofitExceptions {
        placeApi.getPlaceAdblockList().map { it.toPlace() }
    }

    override suspend fun getPagedPlaces(limit: Int, offset: Int) = wrapRetrofitExceptions {
        placeApi.getPagedPlaces(limit, offset).map { it.toPlace() }
    }

}