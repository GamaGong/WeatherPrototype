package com.example.weatherprototype

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
