package com.example.beerservice.app.model.feedback

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer

typealias FeedbackLoader = suspend (pageIndex: Int, pageSize: Int) -> List<FeedbackBeer>

class FeedbackPagingSource(
    private val loader: FeedbackLoader,
    private val pageSize: Int
) : PagingSource<Int, FeedbackBeer>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedbackBeer> {
        val pageIndex = params.key ?: 0
        return try {
            val feedbackBeer = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                feedbackBeer,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (feedbackBeer.size==params.loadSize)pageIndex + (params.loadSize/pageSize)else null
            )
        }catch (e: Exception){
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FeedbackBeer>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}