package com.example.beerservice.app.model.accounts

import com.example.beerservice.app.model.accounts.entities.SignupData

interface AccountsSource {
    suspend fun signIn(login: String, password: String): String

    suspend fun signUp(signUpData: SignupData)
}