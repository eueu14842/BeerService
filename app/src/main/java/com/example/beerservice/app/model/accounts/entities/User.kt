package com.example.beerservice.app.model.accounts.entities

data class User(
    val userId: Int? = null,
    val userName: String? = null,
    val login: String? = null,
    val mail: String? = null,
    val telephoneNumber: String? = null,
    val birthday: String? = null,
    val country: String? = null,
    val city: String? = null,
    val image: String? = null,
    val dateReg: String? = null,
    val totalFeedback: Int? = null


) {
    fun toUserEditData() = UserEditData(
        userName = userName,
        mail = mail,
        telephoneNumber = telephoneNumber,
        birthday = birthday,
        country = country,
        city = city
    )
}