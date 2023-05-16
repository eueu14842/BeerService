package com.example.beerservice.app.model.place

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.place.entities.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlacesRepository(
    val placeSource: PlaceSource
) {
    suspend fun getPlacesList(geoLat: Double, geoLon: Double, visibleRegion: Double) =
        placeSource.getPlaceList(geoLat, geoLon, visibleRegion)

    suspend fun getPlacesAdblockList() = placeSource.getPlacesAdblockList()

    suspend fun getPlaceById(id: Int) = placeSource.getPlaceProfile(id)

    suspend fun getPagedPlaces(): Flow<PagingData<Place>> {
        val loader: PlacePageLoader = { pageIndex, pageSize ->
            getPlaces(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PlacePagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getPlaces(
        pageIndex: Int,
        pageSize: Int
    ): List<Place> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            val list: List<Place> = placeSource.getPagedPlaces(pageSize, offset)
            return@withContext list
        }
}
