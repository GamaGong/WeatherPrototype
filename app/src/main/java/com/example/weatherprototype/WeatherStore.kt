package com.example.weatherprototype

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class WeatherStore(
    private val database: WeatherDatabase,
    private val api: OpenWeatherMapServiceApi,
) {
    @ExperimentalCoroutinesApi
    @FlowPreview
    private val currentWeatherStore = StoreBuilder.from(
        fetcher = Fetcher.of { location: String -> api.currentWeather(location) },
    ).build()

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val sevenDaysForecastStore = StoreBuilder.from(
        fetcher = Fetcher.of { coordinates: Coordinates ->
            api.oneCall(
                lat = coordinates.latitude,
                lon = coordinates.longitude,
                exclude = "current,minutely,hourly,alerts",
            )
        },
    ).build()

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun getCurrentWeather(location: Location) = currentWeatherStore.get(location.name)
        .toDomainModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun getSevenDaysFore(location: Location) = sevenDaysForecastStore.get(location.coordinates)
        .toDomainModel()

    suspend fun getFavoritesLocations() = database.favoriteDao.getAll().map { it.toDomain() }

    suspend fun addFavoriteLocation(location: Location) =
        database.favoriteDao.insert(FavoriteLocation.fromDomain(location))
}
