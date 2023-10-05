package com.example.beerservice.app.model.place.entities

import com.example.beerservice.sources.place.entities.GetPlaceIdUserIdEntity

data class PlaceIdUserId(
    val placeId: Int,
    val userId: Int
) {
    fun toGetPlaceIdUserId() = GetPlaceIdUserIdEntity(
        placeId = placeId,
        userId = userId
    )
}