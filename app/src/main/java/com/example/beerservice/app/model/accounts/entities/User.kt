package com.example.beerservice.app.model.accounts.entities

data class User(
    val userId: Int,
    val userName: String,
    val login: String,
    val mail: String,
    val telephoneNumber: String,
    val birthday: String,
    val country: String,
    val image: String,
    val dateReg: String,
    val totalFeedback: Int
)