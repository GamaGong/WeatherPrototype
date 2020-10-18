package com.example.weatherprototype

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
val currentWeatherStore = StoreBuilder.from(
    fetcher = Fetcher.of { location: String -> OpenWeatherMapService.retrofitService.currentWeather(location) },
).build()

@ExperimentalCoroutinesApi
@FlowPreview
val sevenDaysForecastStore = StoreBuilder.from(
    fetcher = Fetcher.of { coord: Coord ->
        OpenWeatherMapService.retrofitService.oneCall(
            lat = coord.lat ?: 0.0,
            lon = coord.lon ?: 0.0,
            exclude = "current,minutely,hourly,alerts",
        )
    },
).build()
