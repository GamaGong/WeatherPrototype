package com.example.weatherprototype.details.domain

import com.example.weatherprototype.CurrentWeatherResponse
import com.example.weatherprototype.Location
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.common.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetCurrentWeather(private val weatherStore: WeatherStore) :
    UseCase<Location, GetCurrentWeather.Response> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): Response {
        return Response(
            weatherStore.getCurrentWeather(arg)
        )
    }

    data class Response(
        val weather: CurrentWeatherResponse
    )
}