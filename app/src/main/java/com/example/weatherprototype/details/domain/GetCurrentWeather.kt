package com.example.weatherprototype.details.domain

import com.example.weatherprototype.CurrentWeather
import com.example.weatherprototype.IconUrl
import com.example.weatherprototype.Location
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.common.UseCase
import com.example.weatherprototype.details.view.HeaderWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetCurrentWeather(private val weatherStore: WeatherStore) :
    UseCase<Location, HeaderWeather> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): HeaderWeather {
        val isFavourite = weatherStore.getFavoritesLocations().find { it.name == arg.name } != null
        return weatherStore.getCurrentWeatherByName(arg.name)
            .toUiModel(isFavourite)
    }
}

private fun CurrentWeather.toUiModel(isFavourite: Boolean): HeaderWeather = HeaderWeather(
    temperature = "${this.temperature} \u2103",
    description = this.weatherDescription,
    windSpeed = "${this.windSpeed} m/s",
    iconUrl = IconUrl(this.iconUrl),
    sunriseTime = this.sunriseTime,
    sunsetTime = this.sunsetTime,
    weatherLocationName = this.location.name,
    isFeatured = isFavourite
)
