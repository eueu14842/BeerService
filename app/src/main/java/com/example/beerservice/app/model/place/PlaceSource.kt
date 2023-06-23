package com.example.beerservice.app.model.place

import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId

interface PlaceSource {
    suspend fun getPlaceProfile(id: Int): Place
    suspend fun getPlaceList(geoLat: Double, geoLon: Double, visibleRegion: Double): List<Place>
    suspend fun getPlacesAdblockList(): List<Place>
    suspend fun getPagedPlaces(limit: Int, offset: Int): List<Place>
    suspend fun addFavorite(placeIdUserId: PlaceIdUserId)
    suspend fun removeFavorite(placeIdUserId: PlaceIdUserId)
}