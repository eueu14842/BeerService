package com.example.beerservice.sources.feedback.entities

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

class GetFeedbackResponseEntity(
    val id: Int,
    val feedbackText: String,
    val rating: Float,
    val imageFeedback: String,
    val userName: String,
    val breweryName: String,
    val beerName: String,
    val breweryId: Int,
    val beerId: Int
) {
    fun toFeedback() = FeedbackBeer(
        id = id,
        feedbackText = feedbackText,
        rating = rating,
        imageFeedback = imageFeedback,
        userName = userName,
        breweryName = breweryName,
        beerName = beerName,
        breweryId = breweryId,
        beerId = beerId
    )
}