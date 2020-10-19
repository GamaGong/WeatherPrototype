package com.example.weatherprototype.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.Coordinates
import com.example.weatherprototype.CurrentWeatherResponse
import com.example.weatherprototype.Location
import com.example.weatherprototype.OneCalResponse
import com.example.weatherprototype.common.runWithResult
import com.example.weatherprototype.details.domain.GetCurrentWeather
import com.example.weatherprototype.details.domain.GetForecast
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getCurrentWeather: GetCurrentWeather,
    private val getForecast: GetForecast,
) : ViewModel() {
    private val _currentWeather: MutableLiveData<CurrentWeatherResponse> =
        MutableLiveData<CurrentWeatherResponse>()
    val currentWeather: LiveData<CurrentWeatherResponse> = _currentWeather

    private val _weatherForecast: MutableLiveData<OneCalResponse> =
        MutableLiveData<OneCalResponse>()
    val weatherForecast: LiveData<OneCalResponse> = _weatherForecast

    private val _errors: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    val errors: LiveData<Throwable> = _errors

    fun initialLoad() {
        viewModelScope.launch {
            getCurrentWeather.runWithResult(
                arg = Location.Casual(name = "London", coordinates = Coordinates(0.0, 0.0)),
                handleResult = { result ->
                    _currentWeather.postValue(result.weather)
                },
                handleError = { error ->
                    _errors.postValue(error)
                }
            )

            getForecast.runWithResult(
                arg = Location.Casual(name = "London", coordinates = Coordinates(0.0, 0.0)),
                handleResult = { result ->
                    _weatherForecast.postValue(result.weather)
                },
                handleError = { error ->
                    _errors.postValue(error)
                }
            )
        }
    }
}