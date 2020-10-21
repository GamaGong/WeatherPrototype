package com.example.weatherprototype.app.details.domain

import com.example.weatherprototype.app.CurrentWeather
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.details.view.HeaderWeather
import com.example.weatherprototype.app.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetCurrentWeather(private val weatherStore: WeatherStore) :
    UseCase<Location, HeaderWeather> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): HeaderWeather {
        val isFavourite = weatherStore.getFavoriteLocation(arg.name) != null
        return weatherStore.getCurrentWeather(arg.name).toUiModel(isFavourite)
    }
}

private fun CurrentWeather.toUiModel(isFavourite: Boolean): HeaderWeather = HeaderWeather(
    temperature = "${this.temperature} \u2103",
    description = this.weatherDescription,
    windSpeed = "${this.windSpeed} m/s",
    iconUrl = iconUrl,
    sunriseTime = this.sunriseTime,
    sunsetTime = this.sunsetTime,
    weatherLocationName = this.location.name,
    isFeatured = isFavourite,
)
