package com.example.weatherprototype.details.domain

import com.example.weatherprototype.DayWeather
import com.example.weatherprototype.IconUrl
import com.example.weatherprototype.Location
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.common.UseCase
import com.example.weatherprototype.details.list.DetailsListItem
import com.example.weatherprototype.details.pager.DetailsPagerAdapter.DetailsPagerItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class GetForecast(private val weatherStore: WeatherStore) :
    UseCase<Location, List<DetailsPagerItem>> {
    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun run(arg: Location): List<DetailsPagerItem> {
        val sevenDays = weatherStore.getSevenDaysFore(arg)
            .map {
                it.toUiModel()
            }
        return listOf(
            DetailsPagerItem(sevenDays),
            DetailsPagerItem(sevenDays.subList(0, 3))
        )
    }
}

private fun DayWeather.toUiModel() = DetailsListItem(
    weatherIconUrl = IconUrl(iconId = this.iconUrl),
    maxTemperature = "${this.maxTemperature} \u2103",
    minTemperature = "${this.minTemperature} \u2103",
    date = this.date.toLocalDate()
)
