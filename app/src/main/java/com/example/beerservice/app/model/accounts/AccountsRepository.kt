package com.example.beerservice.app.model.accounts

import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.model.settings.AppSettings

class AccountsRepository(
    val accountsSource: AccountsSource,
    val appSettings: AppSettings
) {

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
        // TODO:  Далее реализовать загрузку данных профиля
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

    // TODO: реализовать logout

}