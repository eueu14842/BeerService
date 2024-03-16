package com.example.beerservice.app.model.feedback.entities


data class FeedbackBeerCreate(
    val beerId: Int,
    val feedbackText: String,
    val rating: Int,
    val userId: Int
)