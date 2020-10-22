package com.example.weatherprototype.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteLocation::class,
        HardCodedLocation::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
    abstract val hardCodedDao: HardCodedDao
}