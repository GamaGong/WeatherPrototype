package com.example.weatherprototype

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)

sealed class Location {
    abstract val name: String
    abstract val coordinates: Coordinates

    data class Favorite(override val name: String, override val coordinates: Coordinates) : Location()
    data class Casual(override val name: String, override val coordinates: Coordinates) : Location()
}
