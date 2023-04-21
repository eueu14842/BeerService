package com.example.beerservice.app.model.brewery.entities

data class Brewery(
    val id: Int,
    val name: String,
    val city: String,
    val description: String,
    val type: String ,
    val averageRating: Float,
    val image: String,
) {
}