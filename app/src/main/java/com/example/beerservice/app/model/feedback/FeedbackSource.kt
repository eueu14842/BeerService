package com.example.beerservice.app.model.feedback

import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

interface FeedbackSource {
  suspend  fun getPagedFeedbackByBeerId(beerId: Int, limit: Int, offset: Int): List<FeedbackBeer>
}