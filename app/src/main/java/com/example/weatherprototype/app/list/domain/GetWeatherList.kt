package com.example.weatherprototype.app.list.domain

import com.example.weatherprototype.app.FlowUseCase
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.list.WeatherListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GetWeatherList(
    private val weatherStore: WeatherStore,
) : FlowUseCase<Unit, List<WeatherListItem>> {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private suspend fun Location.toLocationWeather(): WeatherListItem.LocationWeather {
        val currentWeather = weatherStore.getCurrentWeather(name)
        return WeatherListItem.LocationWeather.fromDomain(currentWeather)
    }

    private fun List<Location>.addHeader(header: WeatherListItem.Header) =
        if (isEmpty()) this else listOf(header) + this

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun getFlow(arg: Unit): Flow<List<WeatherListItem>> {
        return weatherStore.getHardCodedLocations()
            .combine(weatherStore.getFavoritesLocations()) { hardCodedList, favoritesList ->
                val distinctFavorites = favoritesList.filter { hardCodedList.contains(it).not() }
                hardCodedList.addHeader(WeatherListItem.Header.hardCoded) +
                        distinctFavorites.addHeader(WeatherListItem.Header.favorites)
            }
            .map { combinedList ->
                combinedList.map {
                    if (it is Location) it.toLocationWeather() else it as WeatherListItem.Header
                }
            }
    }
}