package com.example.beerservice.app.model.place

data class Place(
    val name: String,
    val address: String,
    val description: String,
    val userId: String,
    val typePlace: String,
)