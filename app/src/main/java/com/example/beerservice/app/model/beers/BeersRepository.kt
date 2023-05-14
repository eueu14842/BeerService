package com.example.beerservice.app.model.beers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.beers.entities.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BeersRepository(
    val beersSource: BeersSource,
) {

    suspend fun getBeerById(id: Int) : Beer{
       return beersSource.getBeerById(id)
    }

    suspend fun getBeerList(): List<Beer> {
        return beersSource.getBeerList()
    }

    suspend fun getBeerAdblockList(): List<Beer> {
        return beersSource.getBeerAdblockList()
    }

    suspend fun getPagedBeerByPlaceId(breweryId: Int): Flow<PagingData<Beer>> {
        val loader: BeerPageLoader = { pageIndex, pageSize ->
            getBeersByPlaceId(breweryId, pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BeerPagingSource(loader, PAGE_SIZE) }
        ).flow
    }


    private suspend fun getBeersByPlaceId(
        placeId: Int,
        pageIndex: Int,
        pageSize: Int,
    ): List<Beer> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            return@withContext beersSource.getBeersListByPlaceId(placeId, pageSize, offset)
        }


    suspend fun getPagedBeerByBreweryId(breweryId: Int): Flow<PagingData<Beer>> {
        val loader: BeerPageLoader = { pageIndex, pageSize ->
            getBeersByBreweryId(breweryId, pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BeerPagingSource(loader, PAGE_SIZE) }
        ).flow
    }


    private suspend fun getBeersByBreweryId(
        breweryId: Int,
        pageIndex: Int,
        pageSize: Int,
    ): List<Beer> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            return@withContext beersSource.getBeersListByBreweryId(breweryId, pageSize, offset)
        }

    suspend fun getPagedBeer(): Flow<PagingData<Beer>> {
        val loader: BeerPageLoader = { pageIndex, pageSize ->
            getBeers(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BeerPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getBeers(
        pageIndex: Int,
        pageSize: Int,
    ): List<Beer> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            val list: List<Beer> = beersSource.getPagedBeer(pageSize, offset)
            return@withContext list
        }


}