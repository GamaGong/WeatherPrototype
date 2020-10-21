package com.example.weatherprototype.app.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.runWithResult
import com.example.weatherprototype.app.details.domain.ChangeFavouriteState
import com.example.weatherprototype.app.details.domain.GetCurrentWeather
import com.example.weatherprototype.app.details.domain.GetForecast
import com.example.weatherprototype.app.details.pager.DetailsPagerAdapter.DetailsPagerItem
import com.example.weatherprototype.app.details.view.HeaderWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getCurrentWeather: GetCurrentWeather,
    private val getForecast: GetForecast,
    private val changeFavouriteState: ChangeFavouriteState
) : ViewModel() {
    private val _currentWeather: MutableLiveData<HeaderWeather> =
        MutableLiveData<HeaderWeather>()
    val currentWeather: LiveData<HeaderWeather> = _currentWeather

    private val _weatherForecast: MutableLiveData<List<DetailsPagerItem>> =
        MutableLiveData<List<DetailsPagerItem>>()
    val weatherForecast: LiveData<List<DetailsPagerItem>> = _weatherForecast

    private val _errors: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    val errors: LiveData<Throwable> = _errors

    private var savedLocation: Location? = null

    fun initialLoad(location: Location) {
        savedLocation = location
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
        savedLocation?.let {
            viewModelScope.launch(Dispatchers.IO) {
                changeFavouriteState.runWithResult(
                    arg = it,
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
}