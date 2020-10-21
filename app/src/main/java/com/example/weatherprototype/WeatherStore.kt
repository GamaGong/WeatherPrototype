package com.example.weatherprototype

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map

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
    suspend fun getCurrentWeatherByName(locationName: String) =
        currentWeatherStore.get(locationName).toDomainModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun getSevenDaysFore(location: Location) =
        sevenDaysForecastStore.get(location.coordinates)
            .toDomainModel()

    fun getFavoritesLocations() = database.favoriteDao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun getFavoritesLocationsSuspend() = database.favoriteDao.getAllSuspend()

    suspend fun addFavoriteLocation(location: Location) =
        database.favoriteDao.insert(FavoriteLocation.fromDomain(location))

    suspend fun removeFavoriteLocation(locationName: String) =
        database.favoriteDao.remove(locationName)
}
