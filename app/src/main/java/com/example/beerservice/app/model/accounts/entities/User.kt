package com.example.beerservice.app.model.accounts.entities

data class User(
    val name: String,
    val lastName: String,
    val mail: String,
    val userName: String,
    val birthday: String,
    val country: String,
    val login: String,
    val password: String,
)