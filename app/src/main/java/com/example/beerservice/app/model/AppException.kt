package com.example.beerservice.app.model


open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, boolean: Boolean) : super(message )

}

class EmptyFieldException(
    val field: Field
) : AppException()

class AuthException(
    cause: Throwable
) : AppException(cause = cause)

class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class InvalidCredentialsException(cause: Exception) : AppException(cause = cause)
class PasswordMismatchException : AppException()
class AccountAlreadyExistsException(
    cause: Throwable
) : AppException(cause = cause)

class ParseBackendResponseException(cause: Throwable) : AppException(cause = cause)

class ConnectionException(cause: Throwable) : AppException(cause)

internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
    try {
        return block.invoke()
    } catch (e: BackendException) {
        if (e.code == 401) {
            throw AuthException(e)
        } else {
            throw e
        }
    }
}
