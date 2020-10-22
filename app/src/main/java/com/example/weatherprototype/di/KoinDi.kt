package com.example.weatherprototype.di

import androidx.room.Room
import com.example.weatherprototype.app.Location
import com.example.weatherprototype.app.MainActivityViewModel
import com.example.weatherprototype.app.WeatherStore
import com.example.weatherprototype.app.details.DetailsViewModel
import com.example.weatherprototype.app.details.domain.ChangeFavouriteState
import com.example.weatherprototype.app.details.domain.GetCurrentWeather
import com.example.weatherprototype.app.details.domain.GetForecast
import com.example.weatherprototype.app.list.WeatherListViewModel
import com.example.weatherprototype.app.list.domain.GetWeatherList
import com.example.weatherprototype.app.search.SearchDialogViewModel
import com.example.weatherprototype.app.search.domain.SearchLocation
import com.example.weatherprototype.database.WeatherDatabase
import com.example.weatherprototype.network.AuthenticationInterceptor
import com.example.weatherprototype.network.OpenWeatherMapServiceApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object KoinDi {
    private val serviceModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(AuthenticationInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
                .build()
        }
        single {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        single {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .client(get())
                .build()
                .create(OpenWeatherMapServiceApi::class.java)
        }
    }

    private val dataModule = module {
        single {
            Room.databaseBuilder(
                get(),
                WeatherDatabase::class.java,
                "weather_database"
            ).fallbackToDestructiveMigration().build()
        }
        single { WeatherStore(get(), get()) }
    }

    private val useCaseModule = module {
        factory { GetCurrentWeather(get()) }
        factory { GetForecast(get()) }
        factory { ChangeFavouriteState(get()) }
        factory { SearchLocation(get()) }
        factory { GetWeatherList(get()) }
    }

    private val viewModels = module {
        viewModel {(location: Location) -> DetailsViewModel(get(), get(), get(), location = location) }
        viewModel { WeatherListViewModel(get()) }
        viewModel { SearchDialogViewModel(get()) }
        viewModel { MainActivityViewModel(get()) }
    }

    val koinModules = listOf(
        serviceModule,
        dataModule,
        useCaseModule,
        viewModels
    )
}
