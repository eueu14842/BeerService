package com.example.beerservice.app.model

import android.app.Application
import com.example.beerservice.app.Const
import com.yandex.mapkit.MapKitFactory

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(Const.MAPKIT_API_KEY)
    }
}