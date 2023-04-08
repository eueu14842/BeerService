package com.example.beerservice.app.model.place

import com.example.beerservice.app.model.place.entities.Place

interface PlaceSource {
//    suspend fun createPlace()
//    suspend fun getPlaceProfile() : Place
    suspend fun getPlaceList(): List<Place>
//    suspend fun getPlacesByBeerId(): Place
    suspend fun getPlacesAdblockList(): List<Place>


}