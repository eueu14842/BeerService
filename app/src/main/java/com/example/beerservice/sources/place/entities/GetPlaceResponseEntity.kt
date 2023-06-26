package com.example.beerservice.sources.place.entities

import com.example.beerservice.app.model.place.entities.Place

class GetPlaceResponseEntity(
    val placeId: Int? = null,
    val name: String? = null,
    val address: String? = null,
    val description: String? = null,
    val type: String? = null,
    val image: String? = null,
    val city: String? = null,
    val geoLat: Double? = null,
    val geoLon: Double? = null,
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