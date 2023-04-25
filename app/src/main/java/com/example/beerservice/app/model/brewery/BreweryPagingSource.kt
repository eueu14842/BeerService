package com.example.beerservice.app.model.brewery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.brewery.entities.Brewery

typealias BreweryPageLoader = suspend (pageIndex: Int,pageSize: Int) -> List<Brewery>

class BreweryPagingSource(
    private val loader: BreweryPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Brewery>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Brewery> {
        val pageIndex = params.key ?: 0
        return try {
            val brewery: List<Brewery> = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = brewery,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (brewery.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Brewery>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }


}