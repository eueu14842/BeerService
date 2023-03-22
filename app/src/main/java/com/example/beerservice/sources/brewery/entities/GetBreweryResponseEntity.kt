package com.example.beerservice.sources.brewery.entities

class GetBreweryResponseEntity(
    val id: Int,
    val name: String,
    val city: String,
    val description: String,
    val type: String,
    val averageRating: Int,
    val image: String,
) {
}