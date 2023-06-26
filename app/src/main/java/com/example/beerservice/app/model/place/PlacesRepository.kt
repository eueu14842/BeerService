package com.example.beerservice.app.model.place

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.place.entities.PlaceIdUserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlacesRepository(
    val placeSource: PlaceSource,
    val accountsRepository: AccountsRepository
) {
    suspend fun getPlacesList(geoLat: Double, geoLon: Double, visibleRegion: Double): List<Place> {
        val user = accountsRepository.doGetProfile()
        return placeSource.getPlaceList(user.userId!!, geoLat, geoLon, visibleRegion)
    }

    suspend fun getPlacesAdblockList(): List<Place> {
        val user = accountsRepository.doGetProfile()
        return placeSource.getPlacesAdblockList(user.userId!!)
    }

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
            val user = accountsRepository.doGetProfile()
            val offset = pageIndex * pageSize
            return@withContext placeSource.getPagedPlaces(user.userId!!, pageSize, offset)
        }

    suspend fun setFavorite(placeIdUserId: PlaceIdUserId) {
        placeSource.addFavorite(placeIdUserId)
    }

    suspend fun removeFavorite(placeIdUserId: PlaceIdUserId) {
        placeSource.removeFavorite(placeIdUserId)
    }
}
