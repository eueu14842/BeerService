package com.example.beerservice.sources.accounts.entites

data class SignUpRequestEntity(
    val tel: String? = null,
    val mail: String? = null,
    val userName: String? = null,
    val login: String? = null,
    val password: String? = null
)
