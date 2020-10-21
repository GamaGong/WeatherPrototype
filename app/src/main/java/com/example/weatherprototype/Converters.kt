package com.example.weatherprototype

import java.time.LocalDateTime
import java.time.ZoneOffset


fun CurrentWeatherResponse.toDomainModel(): CurrentWeather = CurrentWeather(
    location = Location(
        name = this.name ?: "",
        coordinates = Coordinates(
            latitude = this.coord?.lat ?: 0.0,
            longitude = this.coord?.lon ?: 0.0
        )
    ),
    temperature = this.main?.temp?.toInt() ?: 0,
    weatherDescription = this.weather?.get(0)?.description ?: "",
    iconUrl = IconUrl(this.weather?.get(0)?.icon ?: ""),
    humidity = this.main?.humidity ?: 0,
    pressure = this.main?.pressure ?: 0,
    windSpeed = this.wind?.speed?.toInt() ?: 0,
    sunriseTime = LocalDateTime.ofEpochSecond(
        this.sys?.sunrise?.toLong() ?: 0L,
        0,
        ZoneOffset.ofTotalSeconds(this.timezone ?: 0)
    ),
    sunsetTime = LocalDateTime.ofEpochSecond(
        this.sys?.sunset?.toLong() ?: 0L,
        0,
        ZoneOffset.ofTotalSeconds(this.timezone ?: 0)
    ),
)

fun OneCalResponse.toDomainModel(): List<DayWeather> {
    val dayWeatherList = mutableListOf<DayWeather>()

    this.daily?.forEach {
        dayWeatherList.add(
            DayWeather(
                maxTemperature = it.temp?.max?.toInt() ?: 0,
                minTemperature = it.temp?.min?.toInt() ?: 0,
                iconUrl = it.weather?.get(0)?.icon ?: "",
                date = LocalDateTime.ofEpochSecond(
                    it.dt?.toLong() ?: 0L,
                    0,
                    ZoneOffset.ofTotalSeconds(this.timezone_offset)
                ),
            )
        )
    }

    return dayWeatherList
}

