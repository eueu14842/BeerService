package com.example.beerservice.app.model.settings


interface AppSettings {

    fun getCurrentToken(): String?

    fun setCurrentToken(token: String?)
}