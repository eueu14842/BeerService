package com.example.beerservice.app.model.brewery.entities

data class Brewery(
    val id: Int? = null,
    val name: String? = null,
    val city: String? = null,
    val description: String? = null,
    val type: String? = null,
    val averageRating: Float? = null,
    val image: String? = null,
)