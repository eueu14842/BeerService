package com.example.beerservice.sources.accounts.entites

import com.example.beerservice.app.model.accounts.entities.User

data class GetUserResponseEntity(
    val userId: Int? = null,
    val userName: String? = null,
    val login: String? = null,
    val mail: String? = null,
    val telephoneNumber: String? = null,
    val birthday: String? = null,
    val city: String? = null,
    val country: String? = null,
    val image: String? = null,
    val dateReg: String? = null,
    val totalFeedback: Int? = null,
    val userRole: Int? = null

) {
    fun toUser(): User = User(
        userId = userId,
        userName = userName,
        login = login,
        mail = mail,
        telephoneNumber = telephoneNumber,
        birthday = birthday,
        country = country,
        city = city,
        image = image,
        dateReg = dateReg,
        totalFeedback = totalFeedback,
        userRole = userRole
    )
}