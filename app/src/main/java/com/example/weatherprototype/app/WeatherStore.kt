package com.example.weatherprototype.app

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import com.example.weatherprototype.database.FavoriteLocation
import com.example.weatherprototype.database.WeatherDatabase
import com.example.weatherprototype.network.OpenWeatherMapServiceApi
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
        fetcher = Fetcher.of { locationName: String -> api.currentWeather(locationName) },
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
    suspend fun getCurrentWeather(locationName: String) =
        currentWeatherStore.get(locationName).toDomainModel()

    @FlowPreview
    @ExperimentalCoroutinesApi
    suspend fun getSevenDaysForecast(coordinates: Coordinates) =
        sevenDaysForecastStore.get(coordinates).toDayForecastList()

    fun getFavoritesLocations() =
        database.favoriteDao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun getFavoriteLocation(locationName: String) = database.favoriteDao.get(locationName)

    suspend fun addFavoriteLocation(location: Location) =
        database.favoriteDao.insert(FavoriteLocation.fromDomain(location))

    suspend fun removeFavoriteLocation(locationName: String) =
        database.favoriteDao.remove(locationName)

    fun getHardCodedLocations() = database.hardCodedDao.getAll().map { list -> list.map { it.toDomain() } }
}
