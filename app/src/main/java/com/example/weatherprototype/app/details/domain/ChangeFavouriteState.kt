package com.example.weatherprototype.app.details.domain

import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.UseCase

/**
 * Return new state true if in favourite else otherwise
 */
class ChangeFavouriteState(private val weatherStore: WeatherStore) : UseCase<Location, Boolean> {
    override suspend fun run(arg: Location): Boolean {
        if (weatherStore.getFavoriteLocation(arg.name) != null) {
            weatherStore.removeFavoriteLocation(arg.name)
        } else {
            weatherStore.addFavoriteLocation(arg)
        }
        return weatherStore.getFavoriteLocation(arg.name) != null
    }
}
