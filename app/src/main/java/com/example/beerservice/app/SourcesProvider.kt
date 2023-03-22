package com.example.beerservice.app

import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource

interface SourcesProvider {
    fun getAccountsSource(): AccountsSource

    fun getBeersSource(): BeersSource

    fun getBrewerySource(): BrewerySource

    fun getFeedbackSource(): FeedbackSource

    fun getPacesSource():PlaceSource
}