package com.example.weatherprototype.app.list

import com.example.weatherprototype.database.HardCodedLocation

object ConstWeather {
    val hardcodedLocations = listOf(
        HardCodedLocation(
            name = "Moscow",
            longitude = 37.62,
            latitude = 55.75,
        ),
        HardCodedLocation(
            name = "Minsk",
            longitude = 27.57,
            latitude = 53.9,
        )
    )
}