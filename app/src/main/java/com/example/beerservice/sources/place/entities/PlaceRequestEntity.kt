package com.example.beerservice.sources.place.entities

import com.example.beerservice.app.model.place.Place

class PlaceRequestEntity(
    val name: String,
    val address: String,
    val description: String,
    val userId: String,
    val typePlace: String,
) {
    fun toPlace(): Place = Place(
        name = name,
        address = address,
        description = description,
        userId = userId,
        typePlace = typePlace
    )
}