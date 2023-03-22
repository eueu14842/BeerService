package com.example.beerservice.sources.feedback

import com.example.beerservice.sources.feedback.entities.FeedbackBeerRequestEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {
    @POST("feedback/createFeedbackBeer")
    fun createFeedbackBeer(@Body body: FeedbackBeerRequestEntity)
}