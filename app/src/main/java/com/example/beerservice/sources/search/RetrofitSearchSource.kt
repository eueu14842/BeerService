package com.example.beerservice.sources.search

import com.example.beerservice.app.model.search.SearchSource
import com.example.beerservice.app.model.search.entities.SearchData
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig

class RetrofitSearchSource(config: RetrofitConfig) : BaseRetrofitSource(config), SearchSource {
    private val searchApi = retrofit.create(SearchApi::class.java)

    override suspend fun getSearchData(userId: Int, searchBy: String): SearchData =
        wrapRetrofitExceptions {
            searchApi.getSearch(userId, searchBy).toSearchData()
        }
}