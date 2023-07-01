package com.example.beerservice.app.model.place

import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId

interface PlaceSource {
    suspend fun getPlaceProfile(id: Int,userId: Int): Place

    suspend fun getPlaceList(userId: Int,geoLat: Double, geoLon: Double, visibleRegion: Double): List<Place>
    suspend fun getPlacesAdblockList(userId: Int): List<Place>
    suspend fun getPagedPlaces(userId: Int,limit: Int, offset: Int): List<Place>

    suspend fun addFavorite(placeIdUserId: PlaceIdUserId)
    suspend fun removeFavorite(placeIdUserId: PlaceIdUserId)
}