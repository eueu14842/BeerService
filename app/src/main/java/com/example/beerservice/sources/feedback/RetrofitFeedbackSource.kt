package com.example.beerservice.sources.feedback

import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig

class RetrofitFeedbackSource(config: RetrofitConfig) : BaseRetrofitSource(config), FeedbackSource {

    private val feedbackApi = config.retrofit.create(FeedbackApi::class.java)

    override suspend fun getPagedFeedbackByBeerId(
        beerId: Int,
        limit: Int,
        offset: Int
    ): List<FeedbackBeer> = wrapRetrofitExceptions {
        feedbackApi.getFeedbackByBeerId(beerId, limit, offset).map { it.toFeedback() }
    }
}