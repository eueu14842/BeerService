package com.example.beerservice.app.model

import android.content.Context
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BreweryRepository
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackRepository
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.app.model.place.PlacesRepository
import com.example.beerservice.app.model.settings.AppSettings
import com.example.beerservice.app.model.settings.SharedPrefAppSettings
import com.example.beerservice.sources.SourceProviderHolder
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory

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
        sourcesProvider.getPlacesSource()
    }

    //repositories
    val accountsRepository: AccountsRepository by lazy {
        AccountsRepository(
            accountsSource = accountsSource,
            appSettings = appSettings
        )
    }

    val breweryRepository: BreweryRepository by lazy {
        BreweryRepository(
            brewerySource = brewerySource
        )
    }

    val beerRepository: BeersRepository by lazy {
        BeersRepository(
            beersSource = beersSource
        )
    }

    val placesRepository: PlacesRepository by lazy {
        PlacesRepository(
            placeSource = placeSource
        )
    }
    val feedbackRepository: FeedbackRepository by lazy {
        FeedbackRepository(
            feedbackSource = feedbackSource
        )
    }


    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }


}