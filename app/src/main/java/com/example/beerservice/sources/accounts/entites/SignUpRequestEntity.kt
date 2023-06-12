package com.example.beerservice.sources.accounts.entites

import com.example.beerservice.app.model.accounts.entities.SignUpData

data class SignUpRequestEntity(
    val tel: String = "",
    val mail: String,
    val userName: String,
    val login: String,
    val password: String

)

