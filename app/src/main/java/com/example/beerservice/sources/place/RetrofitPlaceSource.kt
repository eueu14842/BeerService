package com.example.beerservice.sources.place

import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import com.example.beerservice.sources.search.entities.GetSearchResponseEntity

class RetrofitPlaceSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), PlaceSource {

    private val placeApi = retrofit.create(PlaceApi::class.java)

    override suspend fun getPlaceProfile(id: Int, userId: Int) = wrapRetrofitExceptions {
        placeApi.getPlaceById(id, userId).toPlace()
    }

    override suspend fun getPlaceList(
        userId: Int,
        geoLat: Double,
        geoLon: Double,
        visibleRegion: Double
    ): List<Place> = wrapRetrofitExceptions {
        placeApi.getPlaceList(userId, geoLat, geoLon, visibleRegion).map { it.toPlace() }
    }

    override suspend fun getPlacesAdblockList(userId: Int) = wrapRetrofitExceptions {
        placeApi.getPlaceAdblockList(userId).map { it.toPlace() }
    }

    override suspend fun getPagedPlaces(userId: Int, limit: Int, offset: Int) =
        wrapRetrofitExceptions {
            placeApi.getPagedPlaces(userId, limit, offset).map { it.toPlace() }
        }

    override suspend fun addFavorite(placeIdUserId: PlaceIdUserId) {
        placeApi.addFavorite(placeIdUserId.toGetPlaceIdUserId())
    }

    override suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placeApi.removeFavorite(placeIdUserId.toGetPlaceIdUserId())
    }


}