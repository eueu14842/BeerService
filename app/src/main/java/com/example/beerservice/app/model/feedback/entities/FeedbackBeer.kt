package com.example.beerservice.app.model.feedback.entities

data class FeedbackBeer(
    val id: Int? = null,
    val feedbackText: String? = null,
    val rating: Float? = null,
    val imageFeedback: String? = null,
    val userName: String? = null,
    val breweryName: String? = null,
    val beerName: String? = null,
    val breweryId: Int? = null,
    val beerId: Int? = null
) {
}