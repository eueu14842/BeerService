package com.example.beerservice.app.model.feedback

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.model.wrapBackendExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class FeedbackRepository(
    private val feedbackSource: FeedbackSource
) {
    suspend fun getPagedFeedbackById(beerId: Int): Flow<PagingData<FeedbackBeer>> {
        val loader: FeedbackLoader = { pageIndex, pageSize ->
            getFeedbackByBeerId(beerId, pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedbackPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getFeedbackByBeerId(
        id: Int,
        pageIndex: Int,
        pageSize: Int
    ): List<FeedbackBeer> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            return@withContext feedbackSource.getPagedFeedbackByBeerId(id, pageSize, offset)
        }


    suspend fun createFeedback(
        beerId: Int,
        feedbackText: String,
        rating: Float,
        userId: Int,
        body: MultipartBody.Part? = null
    ) = wrapBackendExceptions {
        feedbackSource.createFeedback(beerId, feedbackText, rating, userId, body)
    }

}