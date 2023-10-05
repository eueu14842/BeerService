package com.example.beerservice.sources.accounts.entites

data class UserEditDataRequestEntity(
    val userName: String? = null,
    val mail: String? = null,
    val telephoneNumber: String? = null,
    val birthday: String? = null,
    val country: String? = null,
    val city: String? = null
)