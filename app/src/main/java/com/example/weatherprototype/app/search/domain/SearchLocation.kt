package com.example.weatherprototype.app.search.domain

import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.UseCase
import com.example.weatherprototype.app.WeatherStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class SearchLocation(
    private val store: WeatherStore
): UseCase<String, Location> {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override suspend fun run(arg: String): Location {
        return store.getCurrentWeather(arg).location
    }
}