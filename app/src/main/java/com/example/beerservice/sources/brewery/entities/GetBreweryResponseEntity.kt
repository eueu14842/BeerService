package com.example.beerservice.sources.brewery.entities

import com.example.beerservice.app.model.brewery.entities.Brewery

class GetBreweryResponseEntity(
    val id: Int? =null,
    val name: String? =null,
    val city: String,
    val description: String? =null,
    val type: String? =null,
    val averageRating: Float? =null,
    val image: String? =null,
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