package com.example.weatherprototype.app.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.list.WeatherListItem.Header.Companion.favorites
import com.example.weatherprototype.app.list.WeatherListItem.Header.Companion.hardCoded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
@FlowPreview
class WeatherListViewModel(private val weatherStore: WeatherStore) : ViewModel() {
    val items: LiveData<List<WeatherListItem>>
        get() = weatherStore.getHardCodedLocations()
            .combine(weatherStore.getFavoritesLocations()) { hardCodedList, favoritesList ->
                val distinctFavorites = favoritesList.filter { hardCodedList.contains(it).not() }
                hardCodedList.addHeader(hardCoded) + distinctFavorites.addHeader(favorites)
            }
            .map { combinedList ->
                combinedList.map {
                    if (it is Location) it.toLocationWeather() else it as WeatherListItem.Header
                }
            }
            .catch { _errors.postValue(it) }
            .asLiveData()

    private suspend fun Location.toLocationWeather(): WeatherListItem.LocationWeather {
        val currentWeather = weatherStore.getCurrentWeather(name)
        return WeatherListItem.LocationWeather.fromDomain(currentWeather)
    }

    private fun List<Location>.addHeader(header: WeatherListItem.Header) =
        if (isEmpty()) this else listOf(header) + this

    private val _errors: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    val errors: LiveData<Throwable> = _errors
}