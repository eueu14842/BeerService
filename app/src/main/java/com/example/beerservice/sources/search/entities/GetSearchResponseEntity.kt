package com.example.beerservice.sources.search.entities

import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.brewery.entities.Brewery
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.app.model.search.entities.SearchData

data class GetSearchResponseEntity(
    val beer: List<Beer>?,
    val brewery: List<Brewery>?,
    val place: List<Place>?
) {
    fun toSearchData(): SearchData {
        return SearchData(
            beer = beer,
            brewery = brewery,
            place = place
        )
    }
}