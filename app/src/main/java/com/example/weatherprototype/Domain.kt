package com.example.weatherprototype

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double,
) : Parcelable

@Parcelize
data class Location(
    val name: String,
    val coordinates: Coordinates,
) : Parcelable

private const val iconUrl = "http://openweathermap.org/img/wn/"
private const val smallIconUrlEnding = ".png"
private const val regularIconUrlEnding = "@2x.png"
private const val largeIconUrlEnding = "@4x.png"

data class IconUrl(private val iconId: String) {
    private val iconUrlBase = "$iconUrl$iconId"
    val small: String get() = "$iconUrlBase$smallIconUrlEnding"
    val regular: String get() = "$iconUrlBase$regularIconUrlEnding"
    val large: String get() = "$iconUrlBase$largeIconUrlEnding"
}

data class CurrentWeather(
    val coordinates: Coordinates,
    val temperature: Int,
    val weatherDescription: String,
    val iconUrl: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Int,
    val sunriseTime: LocalDateTime,
    val sunsetTime: LocalDateTime,
    val weatherLocationName: String,
)

data class DayWeather(
    val maxTemperature: Int,
    val minTemperature: Int,
    val iconUrl: String,
    val date: LocalDateTime
)