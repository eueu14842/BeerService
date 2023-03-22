package com.example.beerservice.sources.accounts.entites

import com.example.beerservice.app.model.accounts.entities.User

class SignUpRequestEntity(
    val name: String,
    val lastName: String,
    val mail: String,
    val userName: String,
    val birthday: String,
    val country: String,
    val login: String,
    val password: String,
) {
    fun toUser(): User = User(
        name = name,
        lastName = lastName,
        mail = mail,
        userName = userName,
        birthday = birthday,
        country = country,
        login = login,
        password = password
    )
}
