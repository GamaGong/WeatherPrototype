package com.example.weatherprototype.app.search

import com.example.weatherprototype.app.Location

sealed class SearchState {
    object Input : SearchState()
    object Loading : SearchState()
    object Error : SearchState()
    data class NavigateToDetails(val location: Location) : SearchState()
}