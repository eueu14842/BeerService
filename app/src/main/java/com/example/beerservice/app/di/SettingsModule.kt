package com.example.beerservice.app.di

import com.example.beerservice.app.model.settings.AppSettings
import com.example.beerservice.app.model.settings.SharedPrefAppSettings
import com.example.beerservice.app.utils.logger.LogCatLogger
import com.example.beerservice.app.utils.logger.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSettings(sharedPrefAppSettings: SharedPrefAppSettings): AppSettings

    @Binds
    abstract fun bindLogger(logger: LogCatLogger): Logger
}