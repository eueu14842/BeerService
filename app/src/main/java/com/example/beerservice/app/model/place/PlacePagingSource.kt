package com.example.beerservice.app.model.place

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerservice.app.model.place.entities.Place

typealias PlacePageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Place>

class BeerPagingSource(
    private val loader: PlacePageLoader,
    private val pageSize: Int
) : PagingSource<Int, Place>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Place> {
        val pageIndex = params.key ?: 0
        return try {
            val places: List<Place> = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = places,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (places.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Place>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }


}