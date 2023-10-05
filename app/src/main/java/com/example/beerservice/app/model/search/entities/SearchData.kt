package com.example.beerservice.app.model.search.entities

import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.model.place.entities.Place

data class SearchData(
    val beer: List<Beer>?,
    val brewery: List<Brewery>?,
    val place: List<Place>?
)