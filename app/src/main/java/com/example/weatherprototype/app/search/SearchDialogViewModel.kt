package com.example.weatherprototype.app.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.app.runWithResult
import com.example.weatherprototype.app.search.domain.SearchLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class SearchDialogViewModel(private val searchLocation: SearchLocation) : ViewModel() {
    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> get() = _state

    init {
        _state.value = SearchState.Input
    }

    fun search(locationName: String) {
        _state.value = SearchState.Loading
        viewModelScope.launch {
            searchLocation.runWithResult(
                arg = locationName,
                handleResult = { _state.postValue(SearchState.NavigateToDetails(it)) },
                handleError = { _state.postValue(SearchState.Error) },
            )
        }
    }
}