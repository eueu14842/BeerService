package com.example.beerservice.sources.accounts.entites

data class SignUpRequestEntity(
    val tel: String,
    val mail: String,
    val userName: String,
    val login: String,
    val password: String
)
