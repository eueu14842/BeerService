package com.example.beerservice.app.model.accounts

import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.accounts.entities.UserEditData

interface AccountsSource {

    suspend fun signIn(login: String, password: String): String

    suspend fun signUp(signUpData: SignUpData)

    suspend fun getUser(): User

    suspend fun updateAccount(userId: Int, userData: UserEditData)
}