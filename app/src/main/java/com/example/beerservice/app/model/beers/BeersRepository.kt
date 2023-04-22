package com.example.beerservice.app.model.beers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.beers.entities.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BeersRepository(
    val beersSource: BeersSource,
) {

    suspend fun getBeerById(id: Int) {
        beersSource.getBeerById(id)
    }

    suspend fun getBeerList(): List<Beer> {
        return beersSource.getBeerList()
    }

    suspend fun getBeerAdblockList(): List<Beer> {
        return beersSource.getBeerAdblockList()
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