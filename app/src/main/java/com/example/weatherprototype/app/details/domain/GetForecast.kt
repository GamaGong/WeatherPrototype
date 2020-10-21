package com.example.weatherprototype.app.details.domain

import com.example.weatherprototype.app.DayForecast
import com.example.weatherprototype.app.IconUrl
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.details.list.DetailsListItem
import com.example.weatherprototype.app.details.pager.DetailsPagerAdapter.DetailsPagerItem
import com.example.weatherprototype.app.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetForecast(private val weatherStore: WeatherStore) :
    UseCase<Location, List<DetailsPagerItem>> {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): List<DetailsPagerItem> {
        val sevenDays = weatherStore.getSevenDaysForecast(arg.coordinates)
            .map {
                it.toUiModel()
            }
        return listOf(
            DetailsPagerItem(sevenDays),
            DetailsPagerItem(sevenDays.subList(0, 3))
        )
    }
}

private fun DayForecast.toUiModel() = DetailsListItem(
    weatherIconUrl = IconUrl(iconId = this.iconUrl),
    maxTemperature = "${this.maxTemperature} \u2103",
    minTemperature = "${this.minTemperature} \u2103",
    date = this.date.toLocalDate()
)
