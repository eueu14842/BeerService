package com.example.beerservice.sources.base

import com.example.beerservice.app.model.SourcesProvider
import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource

class RetrofitSourcesProvider(
    val retrofitConfig: RetrofitConfig
): SourcesProvider {
    override fun getAccountsSource(): AccountsSource {
        TODO("Not yet implemented")
    }

    override fun getBeersSource(): BeersSource {
        TODO("Not yet implemented")
    }

    override fun getBrewerySource(): BrewerySource {
        TODO("Not yet implemented")
    }

    override fun getFeedbackSource(): FeedbackSource {
        TODO("Not yet implemented")
    }

    override fun getPacesSource(): PlaceSource {
        TODO("Not yet implemented")
    }

}