package com.example.beerservice.app.model.beers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerservice.app.model.beers.entities.Beer

typealias BeerPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Beer>

class BeerPagingSource(
    private val loader: BeerPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val pageIndex = params.key ?: 0
        return try {
            val beers: List<Beer> = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = beers,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (beers.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }


}