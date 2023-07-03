package com.example.beerservice.app.model.feedback.entities

import com.example.beerservice.sources.feedback.entities.FeedbackBeerCreateRequestEntity

data class FeedbackBeerCreate(
    val beerId: Int,
    val feedbackText: String,
    val rating: Int,
    val userId: Int
) {
    fun toFeedbackBeerCreateRequestEntity() = FeedbackBeerCreateRequestEntity(
        beerId = beerId,
        feedbackText = feedbackText,
        rating = rating,
        userId = userId
    )
}