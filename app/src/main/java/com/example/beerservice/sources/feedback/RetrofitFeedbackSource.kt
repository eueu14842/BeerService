package com.example.beerservice.sources.feedback

import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFeedbackSource @Inject constructor(config: RetrofitConfig) : BaseRetrofitSource(config), FeedbackSource {

    private val feedbackApi = config.retrofit.create(FeedbackApi::class.java)

    override suspend fun getPagedFeedbackByBeerId(
        beerId: Int,
        limit: Int,
        offset: Int
    ): List<FeedbackBeer> = wrapRetrofitExceptions {
        feedbackApi.getFeedbackByBeerId(beerId, limit, offset).map { it.toFeedback() }
    }


    override suspend fun getPagedFeedbackByUserId(
        userId: Int,
        limit: Int,
        offset: Int
    ): List<FeedbackBeer> = wrapRetrofitExceptions {
        feedbackApi.getFeedbackByUserId(userId, limit, offset).map { it.toFeedback() }
    }

    override suspend fun createFeedback(
        beerId: Int,
        feedbackText: String,
        rating: Float,
        userId: Int,
        body: MultipartBody.Part?
    ) {
        feedbackApi.createFeedbackBeer(beerId, feedbackText, rating, userId, body)
    }
}