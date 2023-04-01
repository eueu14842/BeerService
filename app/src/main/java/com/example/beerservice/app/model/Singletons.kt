package com.example.beerservice.app.model

import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.app.model.settings.AppSettings
import com.example.beerservice.app.model.settings.SharedPrefAppSettings
import com.example.beerservice.sources.SourceProviderHolder

object Singletons {


    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }


    val appSettings: AppSettings by lazy {
        SharedPrefAppSettings(appContext)
    }


    // sources
    private val accountsSource: AccountsSource by lazy {
        sourcesProvider.getAccountsSource()
    }
    private val beersSource: BeersSource by lazy {
        sourcesProvider.getBeersSource()
    }
    private val brewerySource: BrewerySource by lazy {
        sourcesProvider.getBrewerySource()
    }
    private val feedbackSource: FeedbackSource by lazy {
        sourcesProvider.getFeedbackSource()
    }
    private val placeSource: PlaceSource by lazy {
        sourcesProvider.getPacesSource()
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }


}