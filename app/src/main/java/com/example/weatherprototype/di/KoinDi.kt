package com.example.weatherprototype.di

import com.example.weatherprototype.OpenWeatherMapService
import com.example.weatherprototype.OpenWeatherMapServiceApi
import com.example.weatherprototype.WeatherDatabase
import com.example.weatherprototype.WeatherStore
import com.example.weatherprototype.details.DetailsViewModel
import com.example.weatherprototype.details.domain.GetCurrentWeather
import com.example.weatherprototype.details.domain.GetForecast
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

object KoinDi {

    private val serviceModule = module {
        single { OpenWeatherMapService.retrofitService }
    }

    private val dataModule = module {
        single { WeatherDatabase.getInstance(get()) }
        single { WeatherStore(get(), get()) }
    }

    private val useCaseModule = module {
        factory { GetCurrentWeather(get()) }
        factory { GetForecast(get()) }
    }

    private val viewModels = module {
        viewModel { DetailsViewModel(get(), get()) }
    }

    val koinModules = listOf(
        serviceModule,
        dataModule,
        useCaseModule,
        viewModels
    )
}
