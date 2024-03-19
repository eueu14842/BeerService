package com.example.beerservice.sources.accounts.entites

class SignInResponseEntity(
    val refresh: String,
    val error: Boolean,
    val message: String,
    val token: String
)