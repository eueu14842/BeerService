package com.example.beerservice.app.model.accounts.entities

import com.example.beerservice.app.model.EmptyFieldException
import com.example.beerservice.app.model.Field

class SignupData(
    val login: String,
    val password: String
) {
    fun validate() {
        if (login.isBlank()) throw EmptyFieldException(Field.Login)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
    }
}