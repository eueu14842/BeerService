package com.example.beerservice.app.model.place.entities

data class Place(
    val placeId: Int? = null,
    val name: String? = null,
    val address: String? = null,
    val description: String? = null,
    val type: String? = null,
    val image: String? = null,
    val city: String? = null,
    val geoLat: Double? = null,
    val geoLon: Double? = null,
    var setAvailabilityOfSpaceForTheUser: Boolean? = false
)