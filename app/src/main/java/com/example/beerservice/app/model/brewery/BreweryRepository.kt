package com.example.beerservice.app.model.brewery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.beerservice.app.Const.PAGE_SIZE
import com.example.beerservice.app.model.brewery.entities.Brewery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BreweryRepository(
    val brewerySource: BrewerySource
) {

    suspend fun getBreweryList(): List<Brewery> {
        return brewerySource.getBreweryList()
    }

    suspend fun getBreweryAdblockList(): List<Brewery> {
        return brewerySource.getBreweryAdblockList()
    }

    suspend fun getBreweryById(id: Int): Brewery {
        return brewerySource.getBreweryProfileById(id)
    }

    suspend fun getPagedBrewery(searchBy: String): Flow<PagingData<Brewery>> {
        val loader: BreweryPageLoader = { pageIndex, pageSize ->
            getBreweries(pageIndex, pageSize, searchBy)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BreweryPagingSource(loader, PAGE_SIZE) }
        ).flow

    }
    private suspend fun getBreweries(pageIndex: Int, pageSize: Int, searchBy: String): List<Brewery> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            val list: List<Brewery> = brewerySource.getPagedBrewery(pageSize, offset, searchBy)
            return@withContext list
        }

}