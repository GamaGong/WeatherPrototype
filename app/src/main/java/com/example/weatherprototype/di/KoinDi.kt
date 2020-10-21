package com.example.weatherprototype.di

import com.example.weatherprototype.*
import com.example.weatherprototype.details.DetailsViewModel
import com.example.weatherprototype.details.domain.ChangeFavouriteState
import com.example.weatherprototype.details.domain.GetCurrentWeather
import com.example.weatherprototype.details.domain.GetForecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
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
        factory { ChangeFavouriteState(get()) }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val viewModels = module {
        viewModel { DetailsViewModel(get(), get(), get()) }
        viewModel { WeatherListFragment.ViewModel(get()) }
        viewModel { SearchDialog.ViewModel(get()) }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    val koinModules = listOf(
        serviceModule,
        dataModule,
        useCaseModule,
        viewModels
    )
}
