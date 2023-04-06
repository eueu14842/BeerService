package com.example.beerservice.sources.brewery.entities

import com.example.beerservice.app.model.brewery.entities.Brewery

class GetBreweryResponseEntity(
    val id: Int,
    val name: String,
    val city: String,
    val description: String,
    val type: String,
    val averageRating: Float,
    val image: String,
) {
    fun toBrewery() = Brewery(
        id = id,
        name = name,
        city = city,
        description = description,
        type = type,
        averageRating = averageRating,
        image = image
    )
}