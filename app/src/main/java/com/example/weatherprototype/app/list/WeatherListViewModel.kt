package com.example.weatherprototype.app.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.weatherprototype.app.list.domain.GetWeatherList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch

class WeatherListViewModel(
    private val getWeatherList: GetWeatherList,
) : ViewModel() {
    @ExperimentalCoroutinesApi
    @FlowPreview
    val items: LiveData<List<WeatherListItem>>
        get() = getWeatherList.getFlow(Unit)
            .catch { _errors.postValue(it) }
            .asLiveData()

    private val _errors: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    val errors: LiveData<Throwable> = _errors
}