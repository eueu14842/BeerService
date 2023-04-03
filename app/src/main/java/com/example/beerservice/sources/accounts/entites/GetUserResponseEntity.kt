package com.example.beerservice.sources.accounts.entites

import com.example.beerservice.app.model.accounts.entities.User

data class GetUserResponseEntity(
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

) {
    fun toUser(): User = User(
        userId = userId,
        userName = userName,
        login = login,
        mail = mail,
        telephoneNumber = telephoneNumber,
        birthday = birthday,
        country = country,
        image = image,
        dateReg = dateReg,
        totalFeedback = totalFeedback
    )
}