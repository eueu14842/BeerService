package com.example.beerservice.app.model.beers.entities

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

data class Beer(
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
    )