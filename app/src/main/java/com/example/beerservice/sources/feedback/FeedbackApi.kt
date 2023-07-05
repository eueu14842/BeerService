package com.example.beerservice.sources.feedback

import com.example.beerservice.sources.feedback.entities.FeedbackBeerCreateRequestEntity
import com.example.beerservice.sources.feedback.entities.GetFeedbackResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FeedbackApi {
    @POST("feedback/create")
    suspend fun createFeedbackBeer(@Body body: FeedbackBeerCreateRequestEntity)

    @GET("feedback/list/beer")
    suspend fun getFeedbackByBeerId(
        @Query("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GetFeedbackResponseEntity>
}