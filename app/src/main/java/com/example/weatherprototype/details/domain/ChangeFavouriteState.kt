package com.example.weatherprototype.details.domain

import com.example.weatherprototype.Location
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.common.UseCase

/**
 * Return new state true if in favourite else otherwise
 */
class ChangeFavouriteState(private val weatherStore: WeatherStore) : UseCase<Location, Boolean> {
    override suspend fun run(arg: Location): Boolean {
        if (weatherStore.getFavoritesLocationsSuspend().find { it.name == arg.name } != null) {
            weatherStore.removeFavoriteLocation(arg.name)
        } else {
            weatherStore.addFavoriteLocation(arg)
        }
        return weatherStore.getFavoritesLocationsSuspend().find { it.name == arg.name } != null
    }
}
