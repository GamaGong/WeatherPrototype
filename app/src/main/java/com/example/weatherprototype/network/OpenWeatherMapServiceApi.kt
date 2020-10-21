package com.example.weatherprototype.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapServiceApi {
    @GET("weather")
    suspend fun currentWeather(
        @Query("q") location: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse

    @GET("forecast")
    suspend fun fiveDaysForecast(
        @Query("q") location: String,
    ): FiveDaysForecastResponse

    @GET("onecall")
    suspend fun oneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String? = null,
        @Query("units") units: String = "metric"
    ): OneCalResponse
}
