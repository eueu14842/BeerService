package com.example.beerservice.app.model.accounts.entities

import com.example.beerservice.app.model.EmptyFieldException
import com.example.beerservice.app.model.Field
import com.example.beerservice.sources.accounts.entites.SignUpRequestEntity
import kotlin.math.log

class SignUpData(
    val tel: String = "",
    val mail: String,
    val userName: String,
    val login: String,
    val password: String
) {
    fun validate() {
        if (login.isBlank()) throw EmptyFieldException(Field.Login)
        if (userName.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (mail.isBlank()) throw EmptyFieldException(Field.Mail)
    }


}