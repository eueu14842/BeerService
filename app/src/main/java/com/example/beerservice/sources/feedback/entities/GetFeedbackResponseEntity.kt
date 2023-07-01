package com.example.beerservice.sources.feedback.entities

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

class GetFeedbackResponseEntity(
    val id: Int? = null,
    val feedbackText: String? = null,
    val rating: Float? = null,
    val imageFeedback: String? = null,
    val userName: String? = null,
    val breweryName: String? = null,
    val beerName: String? = null,
    val breweryId: Int? = null,
    val beerId: Int? = null,
    val imageBeer: String? = null
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
        beerId = beerId,
        imageBeer = imageBeer
    )
}