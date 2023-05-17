package com.example.beerservice.app.model.beers.entities

data class Beer(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val averageRating: Float? = null,
    val style: String? = null,
    val image: String? = null,
    val abv: Float? = null,
    val ibu: Float? = null,
    val breweryId: Int? = null,
    val totalReviews: Int? = null,
)