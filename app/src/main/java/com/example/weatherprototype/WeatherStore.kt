package com.example.weatherprototype

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
val store = StoreBuilder.from(
    fetcher = Fetcher.of { location: String -> OpenWeatherMapService.retrofitService.getCurrentWeather(location) },
).build()