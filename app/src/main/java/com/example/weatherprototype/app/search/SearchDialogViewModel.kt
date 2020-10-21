package com.example.weatherprototype.app.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.app.WeatherStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class SearchDialogViewModel(private val store: WeatherStore) : ViewModel() {
    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> get() = _state

    init {
        _state.value = SearchState.Input
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun search(locationName: String) {
        _state.value = SearchState.Loading
        viewModelScope.launch {
            try {
                val currentWeather = store.getCurrentWeather(locationName)
                _state.value = SearchState.NavigateToDetails(currentWeather.location)
            } catch (any: Throwable) {
                _state.value = SearchState.Error
            }
        }
    }
}