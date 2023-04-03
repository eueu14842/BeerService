package com.example.beerservice.app.model

import com.squareup.moshi.JsonDataException


open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class EmptyFieldException(
    val field: Field
) : AppException()

class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class InvalidCredentialsException(cause: Exception) : AppException(cause = cause)

class AccountAlreadyExistsException(
    cause: Throwable
) : AppException(cause = cause)

class ParseBackendResponseException(cause: Throwable) : AppException(cause)
class ConnectionException(cause: Throwable) : AppException(cause)

// TODO: wrapBackendExceptions?, PasswordMismatchException?