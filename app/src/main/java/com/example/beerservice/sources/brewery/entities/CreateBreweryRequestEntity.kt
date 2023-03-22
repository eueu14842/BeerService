package com.example.beerservice.sources.brewery.entities

data class CreateBreweryRequestEntity(
    val name: String,
    val city: String,
    val description: String,
    val type: String,
    val userId: Int,
)