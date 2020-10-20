package com.example.weatherprototype

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val baseUrl = "https://api.openweathermap.org/data/2.5/"
const val apiKey = "28947a0b8a65cc9821c087c4ac3fab24"

private val client = OkHttpClient.Builder()
    .addInterceptor(AuthenticationInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(baseUrl)
    .client(client)
    .build()

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

object OpenWeatherMapService {
    val retrofitService: OpenWeatherMapServiceApi by lazy { retrofit.create(OpenWeatherMapServiceApi::class.java) }
}

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .url(url = "${original.url}&appid=$apiKey")
        val request: Request = builder.build()
        return chain.proceed(request)
    }
}
