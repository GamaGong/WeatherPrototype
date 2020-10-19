package com.example.weatherprototype

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locations_table")
data class FavoriteLocation(
    @PrimaryKey(autoGenerate = true)
    var favoriteId: Long = 0L,
    @ColumnInfo(name = "location_name")
    val name: String,
    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        fun fromDomain(location: Location.Favorite) = FavoriteLocation(
            name = location.name,
            longitude = location.coordinates.longitude,
            latitude = location.coordinates.latitude,
        )
    }

    fun toDomain() = Location.Favorite(
        name = this.name,
        coordinates = Coordinates(
            latitude = latitude,
            longitude = longitude,
        ),
    )
}
