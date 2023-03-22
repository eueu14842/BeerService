package com.example.beerservice.app.model.beers.entities

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

data class Beer(
    val id: Int,
    val name: String,
    val description: String,
    val averageRating: Int,
    val style: String,
    val abv: Int,
    val ibu: Int,
    val breweryId: Int,
    val totalReviews: Int,
    val feedbackBeer: FeedbackBeer,
    )