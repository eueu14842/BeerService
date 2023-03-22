package com.example.beerservice.app.model.feedback.entities

data class FeedbackBeer(
    val id: Int,
    val feedbackText: String,
    val rating: Int,
    val imageFeedback: String,
    val userName: String,
    val breweryName: String,
) {
}