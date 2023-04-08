package com.example.beerservice.app.model.place


class PlacesRepository(
    val placeSource: PlaceSource
) {
    suspend fun getPlacesList() = placeSource.getPlaceList()
    suspend fun getPlacesAdblockList() = placeSource.getPlacesAdblockList()
}
