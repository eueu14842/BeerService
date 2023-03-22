package com.example.beerservice.app.model


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

// TODO: wrapBackendExceptions?, PasswordMismatchException?