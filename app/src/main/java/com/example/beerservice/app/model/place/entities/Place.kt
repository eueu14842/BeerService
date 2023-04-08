package com.example.beerservice.app.model.place.entities

data class Place(
    val placeId: Int,
    val name: String,
    val address: String,
    val description: String,
    val type: String,
    val image: String,
    val city: String,
    val geoLat: String,
    val geoLon: String,
)