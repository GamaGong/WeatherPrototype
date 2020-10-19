package com.example.weatherprototype.details.domain

import com.example.weatherprototype.Location
import com.example.weatherprototype.OneCalResponse
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.common.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetForecast(private val weatherStore: WeatherStore) : UseCase<Location, GetForecast.Response> {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): Response {
        return Response(weatherStore.getSevenDaysFore(arg))
    }

    data class Response(
        val weather: OneCalResponse
    )
}