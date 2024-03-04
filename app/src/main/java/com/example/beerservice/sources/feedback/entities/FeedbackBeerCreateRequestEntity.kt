package com.example.beerservice.sources.feedback.entities

data class FeedbackBeerCreateRequestEntity(
    val beerId: Int,
    val feedbackText: String,
    val rating: Int,
    val userId: Int,
    val image: String
) {

}
