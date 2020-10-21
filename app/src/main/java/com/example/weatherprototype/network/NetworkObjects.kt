package com.example.weatherprototype.network

import com.example.weatherprototype.app.*
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Coord(
    //City geo location, longitude
    val lon: Double?,
    //City geo location, latitude
    val lat: Double?,
)

data class Weather(
    //Weather condition id
    val id: Int?,
    //Group of weather parameters (Rain, Snow, Extreme etc.)
    val main: String?,
    //Weather condition within the group. You can get the output in your language
    val description: String?,
    //Weather icon id
    val icon: String?,
)

data class Main(
    //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    val temp: Double?,
    //Temperature. This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    val feels_like: Double?,
    //Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    val temp_min: Double?,
    //Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    val temp_max: Double?,
    //Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
    val pressure: Int?,
    //Humidity, %
    val humidity: Int?,
    //Atmospheric pressure on the sea level, hPa
    val sea_level: Double?,
    //Atmospheric pressure on the ground level, hPa
    val grnd_level: Double?,
)

data class Wind(
    //Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    val speed: Double?,
    //Wind direction, degrees (meteorological)
    val deg: Int?,
    //Wind gust. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour
    val gust: Double?,
)

data class Clouds(
    //Cloudiness, %
    val all: Int?,
)

data class Rain(
    //Rain volume for the last 1 hour, mm
    val `1h`: Double?,
    //Rain volume for the last 3 hours, mm
    val `2h`: Double?,
)

data class Snow(
    //Snow volume for the last 1 hour, mm
    val `1h`: Double?,
    //Snow volume for the last 3 hours, mm
    val `2h`: Double?,
)

data class Sys(
    //Internal parameter
    val type: Int?,
    //Internal parameter
    val id: Int?,
    //Internal parameter
    val message: Double?,
    //Country code (GB, JP etc.)
    val country: String?,
    //Sunrise time, unix, UTC
    val sunrise: Int?,
    //Sunset time, unix, UTC
    val sunset: Int?,
)

data class CurrentWeatherResponse(
    val coord: Coord?,
    val weather: List<Weather>?,
    //Internal parameter
    val base: String?,
    val main: Main?,
    //Average visibility, metres
    val visibility: Int?,
    val wind: Wind?,
    val clouds: Clouds?,
    val rain: Rain?,
    val snow: Snow?,
    //Time of data calculation, unix, UTC
    val dt: Int?,
    val sys: Sys?,
    //Shift in seconds from UTC
    val timezone: Int?,
    //City ID
    val id: Int?,
    //City name
    val name: String?,
    //Internal parameter
    val cod: Int?,
) {
    fun toDomainModel(): CurrentWeather = CurrentWeather(
        location = Location(
            name = this.name ?: "",
            coordinates = Coordinates(
                latitude = this.coord?.lat ?: 0.0,
                longitude = this.coord?.lon ?: 0.0,
            ),
        ),
        temperature = this.main?.temp?.toInt() ?: 0,
        weatherDescription = this.weather?.get(0)?.description ?: "",
        iconUrl = IconUrl(this.weather?.get(0)?.icon ?: ""),
        windSpeed = this.wind?.speed?.toInt() ?: 0,
        sunriseTime = this.sys?.sunrise.toLocalDateTime(timezone.toZoneOffset()),
        sunsetTime = this.sys?.sunset.toLocalDateTime(timezone.toZoneOffset()),
    )
}

private fun Int?.toLocalDateTime(offset: ZoneOffset) = LocalDateTime.ofEpochSecond(
    this?.toLong() ?: 0L,
    0,
    offset,
)

private fun Int?.toZoneOffset() = ZoneOffset.ofTotalSeconds(this ?: 0)

data class City(
    //City ID
    val id: Int?,
    //City name
    val name: String?,
    val coord: Coord?,
    //Country code (GB, JP etc.)
    val country: String?,
    //Shift in seconds from UTC
    val timezone: Int?,
    val sunrise: Int?,
    val sunset: Int?,
)

data class Forecast(
    //Time of data forecasted, unix, UTC
    val dt: Int?,
    val main: Main?,
    val weather: List<Weather>?,
    val clouds: Clouds?,
    val wind: Wind?,
    //Average visibility, metres
    val visibility: Int?,
    //Probability of precipitation
    val pop: Double?,
    val rain: Rain?,
    val snow: Snow?,
    val sys: Sys?,
    //Time of data forecasted, ISO, UTC
    val dt_txt: String?,
)

data class FiveDaysForecastResponse(
    //Internal parameter
    val cod: Int?,
    //Internal parameter
    val message: Int?,
    //A number of timestamps returned in the API response
    val cnt: Int?,
    val list: List<Forecast>?,
    val city: City?,
)

data class Feels_like(
    //Day temperature.
    val day: Double?,
    //Night temperature.
    val night: Double?,
    //Evening temperature.
    val eve: Double?,
    //Morning temperature.
    val morn: Double?,
)

data class Temp(
    //Day temperature.
    val day: Double?,
    //Min daily temperature.
    val min: Double?,
    //Max daily temperature.
    val max: Double?,
    //Night temperature.
    val night: Double?,
    //Evening temperature.
    val eve: Double?,
    //Morning temperature.
    val morn: Double?,
)

data class Daily(
    //Time of the forecasted data, Unix, UTC
    val dt: Int?,
    //Sunrise time, Unix, UTC
    val sunrise: Int?,
    //Sunset time, Unix, UTC
    val sunset: Int?,
    //Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val temp: Temp?,
    //This accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val feels_like: Feels_like?,
    //Atmospheric pressure on the sea level, hPa
    val pressure: Int?,
    //Humidity, %
    val humidity: Int?,
    //Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val dew_point: Double?,
    //Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
    val wind_speed: Double?,
    //Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
    val wind_gust: Int?,
    //Wind direction, degrees (meteorological)
    val wind_deg: Int?,
    val weather: List<Weather>?,
    //Cloudiness, %
    val clouds: Int?,
    //Probability of precipitation
    val pop: Double?,
    //Precipitation volume, mm
    val rain: Double?,
    //Midday UV index
    val uvi: Double?,
    //Snow volume, mm
    val snow: Double?,
) {
    fun toDomain(offset: ZoneOffset) = DayForecast(
        maxTemperature = this.temp?.max?.toInt() ?: 0,
        minTemperature = this.temp?.min?.toInt() ?: 0,
        iconUrl = this.weather?.get(0)?.icon ?: "",
        date = this.dt.toLocalDateTime(offset),
    )
}

data class Current(
    //Current time, Unix, UTC
    val dt: Int?,
    //Sunrise time, Unix, UTC
    val sunrise: Int?,
    //Sunset time, Unix, UTC
    val sunset: Int?,
    //Temperature. Units - default: kelvin, metric: Celsius, imperial: Fahrenheit
    val temp: Double?,
    //Temperature. This temperature parameter accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val feels_like: Double?,
    //Atmospheric pressure on the sea level, hPa
    val pressure: Int?,
    //Humidity, %
    val humidity: Int?,
    //AAtmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val dew_point: Double?,
    //Midday UV index
    val uvi: Double?,
    //Cloudiness, %
    val clouds: Int?,
    //Average visibility, metres
    val visibility: Int?,
    //Wind speed. Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour.
    val wind_speed: Double?,
    //Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    val wind_gust: Double?,
    //Wind direction, degrees (meteorological)
    val wind_deg: Int?,
    val weather: List<Weather>?,
    val rain: Rain?,
    val snow: Snow?,
)

data class Minutely(
    //Time of the forecasted data, unix, UTC
    val dt: Int?,
    //Precipitation volume, mm
    val precipitation: Double?,
)

data class Hourly(
    //Time of the forecasted data, Unix, UTC
    val dt: Int?,
    //Temperature. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit
    val temp: Double?,
    //Temperature. This accounts for the human perception of weather. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val feels_like: Double?,
    //Atmospheric pressure on the sea level, hPa
    val pressure: Int?,
    //Humidity, %
    val humidity: Int?,
    //Atmospheric temperature (varying according to pressure and humidity) below which water droplets begin to condense and dew can form. Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    val dew_point: Double?,
    //Cloudiness, %
    val clouds: Int?,
    //Average visibility, metres
    val visibility: Int?,
    //Wind speed. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    val wind_speed: Double?,
    //Wind gust. Units – default: metre/sec, metric: metre/sec, imperial: miles/hour
    val wind_deg: Int?,
    val weather: List<Weather>?,
    //Probability of precipitation
    val pop: Double?,
    val rain: Rain?,
    val snow: Snow?
)

data class Alert(
    //Name of the alert source
    val sender_name: String?,
    //Alert event name
    val event: String?,
    //Date and time of the start of the alert, Unix, UTC
    val start: Int?,
    //Date and time of the end of the alert, Unix, UTC
    val end: Int?,
    //Description of the alert
    val description: String?,
)

data class OneCalResponse(
    //Geographical coordinates of the location (latitude)
    val lat: Double?,
    //Geographical coordinates of the location (longitude)
    val lon: Double?,
    //Timezone name for the requested location
    val timezone: String?,
    //Shift in seconds from UTC
    val timezone_offset: Int,
    //Current weather data API response
    val current: Current?,
    //Minute forecast weather data API response
    val minutely: List<Minutely>?,
    //Hourly forecast weather data API response
    val hourly: List<Hourly>?,
    //Daily forecast weather data API response
    val daily: List<Daily>?,
    //Government weather alerts data from major national weather warning systems
    val alerts: List<Alert>?,
) {
    fun toDayForecastList(): List<DayForecast> {
        val offset = timezone_offset.toZoneOffset()
        return daily?.map { it.toDomain(offset) } ?: emptyList()
    }
}
