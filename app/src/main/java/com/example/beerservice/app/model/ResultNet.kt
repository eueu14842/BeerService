package com.example.beerservice.app.model

sealed class ResultNet<T> {

    fun <R> map(mapper: ((T) -> R)? = null): ResultNet<R> {
        return when (this) {
            is Success -> if (mapper == null) {
                throw java.lang.IllegalStateException("cant map")
            } else {
                Success(mapper(this.value))
            }
            is ErrorResult -> ErrorResult(this.error)
            is Pending -> Pending()

        }
    }

}

class Success<T>(
    val value: T
) : ResultNet<T>()

class ErrorResult<T>(
    val error: Throwable
) : ResultNet<T>()

class Pending<T> : ResultNet<T>()