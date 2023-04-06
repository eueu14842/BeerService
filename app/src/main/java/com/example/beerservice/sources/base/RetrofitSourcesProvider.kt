package com.example.beerservice.sources.base

import com.example.beerservice.app.model.SourcesProvider
import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.sources.accounts.RetrofitAccountSource
import com.example.beerservice.sources.brewery.RetrofitBrewerySource

class RetrofitSourcesProvider(
    val retrofitConfig: RetrofitConfig
): SourcesProvider {
    override fun getAccountsSource(): AccountsSource {
        return RetrofitAccountSource(retrofitConfig)
    }

    override fun getBeersSource(): BeersSource {
        TODO("Not yet implemented")
    }

    override fun getBrewerySource(): BrewerySource {
      return RetrofitBrewerySource(retrofitConfig)
    }

    override fun getFeedbackSource(): FeedbackSource {
        TODO("Not yet implemented")
    }

    override fun getPacesSource(): PlaceSource {
        TODO("Not yet implemented")
    }

}