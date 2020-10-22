package com.example.weatherprototype.app.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.details.domain.ChangeFavouriteState
import com.example.weatherprototype.app.details.domain.GetCurrentWeather
import com.example.weatherprototype.app.details.domain.GetForecast
import com.example.weatherprototype.app.details.pager.DetailsPagerAdapter.DetailsPagerItem
import com.example.weatherprototype.app.details.view.HeaderWeather
import com.example.weatherprototype.app.runWithResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getCurrentWeather: GetCurrentWeather,
    private val getForecast: GetForecast,
    private val changeFavouriteState: ChangeFavouriteState,
    private val location: Location,
) : ViewModel() {
    private val _currentWeather: MutableLiveData<HeaderWeather> =
        MutableLiveData()
    val currentWeather: LiveData<HeaderWeather> = _currentWeather

    private val _weatherForecast: MutableLiveData<List<DetailsPagerItem>> =
        MutableLiveData()
    val weatherForecast: LiveData<List<DetailsPagerItem>> = _weatherForecast

    private val _errors: MutableLiveData<Throwable> = MutableLiveData()
    val errors: LiveData<Throwable> = _errors

    init {
        viewModelScope.launch {
            getCurrentWeather.runWithResult(
                arg = Location(name = location.name, coordinates = location.coordinates),
                handleResult = { result ->
                    _currentWeather.postValue(result)
                },
                handleError = { error ->
                    _errors.postValue(error)
                }
            )

            getForecast.runWithResult(
                arg = Location(name = location.name, coordinates = location.coordinates),
                handleResult = { result ->
                    _weatherForecast.postValue(result)
                },
                handleError = { error ->
                    _errors.postValue(error)
                }
            )
        }
    }

    fun featuredChecked() {
        viewModelScope.launch(Dispatchers.IO) {
            changeFavouriteState.runWithResult(
                arg = location,
                handleResult = { result ->
                    _currentWeather.postValue(_currentWeather.value?.copy(isFeatured = result))
                },
                handleError = { error ->
                    _errors.postValue(error)
                }
            )
        }
    }
}