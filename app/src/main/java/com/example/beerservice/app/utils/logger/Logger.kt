package com.example.beerservice.app.utils.logger

interface Logger {

    fun log(tag: String, message: String)

    fun error(tag: String, e: Throwable)

}