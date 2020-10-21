package com.example.weatherprototype.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprototype.R
import com.example.weatherprototype.app.list.ConstWeather.hardcodedLocations
import com.example.weatherprototype.database.HardCodedLocation
import com.example.weatherprototype.database.WeatherDatabase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }
}

class MainActivityViewModel(weatherDatabase: WeatherDatabase) : ViewModel() {
    init {
        viewModelScope.launch {
            hardcodedLocations.forEach {
                weatherDatabase.hardCodedDao.insert(it)
            }
        }
    }
}
