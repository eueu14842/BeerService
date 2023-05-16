package com.example.beerservice.app.model.accounts

import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.settings.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountsRepository(
    val accountsSource: AccountsSource,
    val appSettings: AppSettings
) {

    lateinit var profileResult: ResultState<User>

    /**
     * @throws EmptyFieldException
     * @throws InvalidCredentialsException
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun signIn(login: String, password: String) {
        if (login.isBlank()) throw EmptyFieldException(Field.Login)
        if (password.isBlank()) throw EmptyFieldException(Field.Login)

        val token = try {
            accountsSource.signIn(login, password)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                throw InvalidCredentialsException(e)
            } else
                throw e
        }
        appSettings.setCurrentToken(token)
        profileResult = Success(doGetProfile())
    }

    suspend fun signUp(signupData: SignUpData) {
        signupData.validate()
        try {
            accountsSource.signUp(signupData)
        } catch (e: BackendException) {
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
    }

    fun getAccount(): ResultState<User> {
        return profileResult
    }


    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val user = accountsSource.getUser()
            profileResult = Success(user)
        }
    }


    suspend fun doGetProfile(): User = wrapBackendExceptions {
        try {
            accountsSource.getUser()
        } catch (e: BackendException) {
            if (e.code == 404) throw AuthException(e)
            else throw e
        }
    }

}