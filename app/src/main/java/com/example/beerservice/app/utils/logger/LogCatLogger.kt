package com.example.beerservice.app.utils.logger

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogCatLogger @Inject constructor() : Logger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, e: Throwable) {
        Log.e(tag, "Error!", e)
    }

}