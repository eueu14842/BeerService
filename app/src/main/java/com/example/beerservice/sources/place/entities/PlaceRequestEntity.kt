package com.example.beerservice.sources.place.entities

import com.example.beerservice.app.model.place.entities.Place

class PlaceRequestEntity(
    val placeId: Int,
    val name: String,
    val address: String,
    val description: String,
    val type: String,
    val image: String,
    val city: String,
    val geoLat: String,
    val geoLon: String,
) {
    fun toPlace() = Place(
        placeId = placeId,
        name = name,
        address = address,
        description = description,
        type = type,
        image = image,
        city = city,
        geoLat = geoLat,
        geoLon = geoLon
    )
}