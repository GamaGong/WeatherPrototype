package com.example.weatherprototype.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherprototype.app.Coordinates
import com.example.weatherprototype.app.Location

@Entity(tableName = "favorite_locations_table")
data class FavoriteLocation(
    @PrimaryKey
    val name: String,
    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        fun fromDomain(location: Location) = FavoriteLocation(
            name = location.name,
            longitude = location.coordinates.longitude,
            latitude = location.coordinates.latitude,
        )
    }

    fun toDomain() = Location(
        name = this.name,
        coordinates = Coordinates(
            latitude = latitude,
            longitude = longitude,
        ),
    )
}

@Entity(tableName = "hard_coded_locations_table")
data class HardCodedLocation(
    @PrimaryKey
    val name: String,
    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        fun fromDomain(location: Location) = HardCodedLocation(
            name = location.name,
            longitude = location.coordinates.longitude,
            latitude = location.coordinates.latitude,
        )
    }

    fun toDomain() = Location(
        name = this.name,
        coordinates = Coordinates(
            latitude = latitude,
            longitude = longitude,
        ),
    )
}
