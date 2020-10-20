package com.example.weatherprototype.details.domain

import com.example.weatherprototype.*
import com.example.weatherprototype.common.UseCase
import com.example.weatherprototype.details.list.DetailsListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetForecast(private val weatherStore: WeatherStore) :
    UseCase<Location, List<DetailsListItem>> {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): List<DetailsListItem> {
        return weatherStore.getSevenDaysFore(arg)
            .map {
                it.toUiModel()
            }
    }
}

private fun DayWeather.toUiModel() = DetailsListItem(
    weatherIconUrl = IconUrl(iconId = this.iconUrl),
    maxTemperature = "${this.maxTemperature} \u2103",
    minTemperature = "${this.minTemperature} \u2103",
    date = this.date.toLocalDate()
)
