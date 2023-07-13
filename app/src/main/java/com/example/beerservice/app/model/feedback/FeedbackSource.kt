package com.example.beerservice.app.model.feedback

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import okhttp3.MultipartBody

interface FeedbackSource {
    suspend fun getPagedFeedbackByBeerId(beerId: Int, limit: Int, offset: Int): List<FeedbackBeer>

    suspend fun createFeedback(
        beerId: Int,
        feedbackText: String,
        rating: Float,
        userId: Int,
        body: MultipartBody.Part? = null
    )
}