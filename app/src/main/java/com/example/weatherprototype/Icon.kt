package com.example.weatherprototype

private const val iconUrl = "http://openweathermap.org/img/wn/"
private const val smallIconUrlEnding = ".png"
private const val regularIconUrlEnding = "@2x.png"
private const val largeIconUrlEnding = "@4x.png"

data class Icon(private val iconId: String) {
    private val iconUrlBase = "$iconUrl$iconId"
    val smallUrl: String get() = "$iconUrlBase$smallIconUrlEnding"
    val regularUrl: String get() = "$iconUrlBase$regularIconUrlEnding"
    val largeUrl: String get() = "$iconUrlBase$largeIconUrlEnding"
}