package com.example.weatherprototype.app.list

import androidx.annotation.StringRes
import com.example.weatherprototype.R
import com.example.weatherprototype.app.Coordinates
import com.example.weatherprototype.app.CurrentWeather
import com.example.weatherprototype.app.IconUrl
import com.example.weatherprototype.app.Location as DomainLocation

sealed class WeatherListItem {
    data class Header(@StringRes val titleId: Int) : WeatherListItem() {
        companion object {
            val favorites get() = Header(titleId = R.string.favorites)
            val hardCoded get() = Header(titleId = R.string.hard_coded)
        }
    }

    data class LocationWeather(
        val name: String,
        val temperature: String,
        val weatherIconUrl: IconUrl,
        val coordinates: Coordinates,
    ) : WeatherListItem() {
        companion object {
            fun fromDomain(weather: CurrentWeather) = LocationWeather(
                name = weather.location.name,
                coordinates = weather.location.coordinates,
                weatherIconUrl = weather.iconUrl,
                temperature = "${weather.temperature} \u2103",
            )
        }

        fun toDomain() = DomainLocation(
            name = name,
            coordinates = coordinates,
        )
    }
}