package com.example.beerservice.app.model

sealed class ResultNet<T> {
}

class Success<T>(
    val value: T
) : ResultNet<T>()

class Error<T>(
    val value: Throwable
) : ResultNet<T>()

class Pending<T> : ResultNet<T>()