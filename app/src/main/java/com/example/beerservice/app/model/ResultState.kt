package com.example.beerservice.app.model

import java.lang.IllegalStateException

sealed class ResultState<T> {

    fun <R> map(mapper: ((T) -> R)? = null): ResultState<R> {
        return when (this) {
            is Success -> if (mapper == null) {
                throw IllegalStateException("cant map")
            } else {
                Success(mapper(this.value))
            }
            is ErrorResult -> ErrorResult(this.error)
            is Pending -> Pending()
            is Empty -> Empty()
        }
    }

}

class Success<T>(
    val value: T
) : ResultState<T>()

class ErrorResult<T>(
    val error: Throwable
) : ResultState<T>()

class Pending<T> : ResultState<T>()
class Empty<T>:ResultState<T>()