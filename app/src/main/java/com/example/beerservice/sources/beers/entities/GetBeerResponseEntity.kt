package com.example.beerservice.sources.beers.entities

import com.example.beerservice.app.model.beers.entities.Beer

class GetBeerResponseEntity(
    val id: Int,
    val name: String,
    val description: String,
    val averageRating: Float,
    val style: String,
    val image: String,
    val abv: Float?,
    val ibu: Float?,
    val breweryId: Int,
    val totalReviews: Int,
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