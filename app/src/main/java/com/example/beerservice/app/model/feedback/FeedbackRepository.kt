package com.example.beerservice.app.model.feedback

import android.graphics.pdf.PdfDocument.Page
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FeedbackRepository(
    val feedbackSource: FeedbackSource
) {

    suspend fun getPagedFeedbackByBeerId(beerId: Int): Flow<PagingData<FeedbackBeer>> {
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


}