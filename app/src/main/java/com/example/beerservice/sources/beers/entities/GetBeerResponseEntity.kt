package com.example.beerservice.sources.beers.entities

import com.example.beerservice.app.model.beers.entities.Beer

class GetBeerResponseEntity(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val averageRating: Float? = null,
    val style: String? = null,
    val image: String? = null,
    val abv: Float? = null,
    val ibu: Float?= null,
    val breweryId: Int? = null,
    val totalReviews: Int? = null,
) {
    fun toBeer() = Beer(
        id = id,
        name = name,
        description = description,
        averageRating = averageRating,
        style = style,
        image = image,
        abv = abv,
        ibu = ibu,
        breweryId = breweryId,
        totalReviews = totalReviews
    )
}