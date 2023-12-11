package com.example.beerservice.app.di

import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.beers.BeersSource
import com.example.beerservice.app.model.brewery.BrewerySource
import com.example.beerservice.app.model.feedback.FeedbackSource
import com.example.beerservice.app.model.place.PlaceSource
import com.example.beerservice.app.model.search.SearchSource
import com.example.beerservice.sources.accounts.RetrofitAccountSource
import com.example.beerservice.sources.beers.RetrofitBeerSource
import com.example.beerservice.sources.brewery.RetrofitBrewerySource
import com.example.beerservice.sources.feedback.RetrofitFeedbackSource
import com.example.beerservice.sources.place.RetrofitPlaceSource
import com.example.beerservice.sources.search.RetrofitSearchSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindAccountSource(retrofitAccountsSource: RetrofitAccountSource): AccountsSource

    @Binds
    abstract fun bindBeerSource(retrofitBeerSource: RetrofitBeerSource): BeersSource

    @Binds
    abstract fun bindBrewerySource(retrofitBrewerySource: RetrofitBrewerySource): BrewerySource

    @Binds
    abstract fun bindFeedbackSource(retrofitFeedbackSource: RetrofitFeedbackSource): FeedbackSource

    @Binds
    abstract fun bindPlaceSource(retrofitPlaceSource: RetrofitPlaceSource): PlaceSource

    @Binds
    abstract fun bindSearchSource(retrofitSearchSource: RetrofitSearchSource): SearchSource


}