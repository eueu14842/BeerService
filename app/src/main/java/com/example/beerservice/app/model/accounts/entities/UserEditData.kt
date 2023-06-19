package com.example.beerservice.app.model.accounts.entities

import com.example.beerservice.sources.accounts.entites.UserEditDataRequestEntity

data class UserEditData(
    val userName: String? = null,
    val mail: String? = null,
    val telephoneNumber: String? = null,
    val birthday: String? = null,
    val country: String? = null,
    val city: String? = null
) {
     fun toUserEditDataEntity() = UserEditDataRequestEntity(
        userName = userName,
        mail = mail,
        telephoneNumber = telephoneNumber,
        birthday = birthday,
        country = country,
        city = city
    )
}
