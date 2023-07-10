package com.example.beerservice.sources.feedback

import com.example.beerservice.sources.feedback.entities.FeedbackBeerCreateRequestEntity
import com.example.beerservice.sources.feedback.entities.GetFeedbackResponseEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface FeedbackApi {
    @POST("feedback/create")
    suspend fun createFeedbackBeer(
        @Query("beerId") id: Int,
        @Query("feedbackText") text: String,
        @Query("rating") rating: Int,
        @Query("userId") userId: Int,
        @Body image: ByteArray
    )

    @GET("feedback/list/beer")
    suspend fun getFeedbackByBeerId(
        @Query("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<GetFeedbackResponseEntity>
}