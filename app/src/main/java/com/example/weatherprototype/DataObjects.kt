package com.example.weatherprototype

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
    val gust: Int?,
)

data class Clouds(
    //Cloudiness, %
    val all: Int?,
)

data class Rain(
    //Rain volume for the last 1 hour, mm
    val `1h`: Int?,
    //Rain volume for the last 3 hours, mm
    val `2h`: Int?,
)

data class Snow(
    //Snow volume for the last 1 hour, mm
    val `1h`: Int?,
    //Snow volume for the last 3 hours, mm
    val `2h`: Int?,
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
)

data class City (
    //City ID
    val id : Int?,
    //City name
    val name : String?,
    val coord : Coord?,
    //Country code (GB, JP etc.)
    val country : String?,
    //Shift in seconds from UTC
    val timezone : Int?,
    val sunrise : Int?,
    val sunset : Int?,
)

data class Forecast(
    //Time of data forecasted, unix, UTC
    val dt : Int?,
    val main : Main?,
    val weather : List<Weather>?,
    val clouds : Clouds?,
    val wind : Wind?,
    //Average visibility, metres
    val visibility : Int?,
    //Probability of precipitation
    val pop : Double?,
    val rain : Rain?,
    val snow: Snow?,
    val sys : Sys?,
    //Time of data forecasted, ISO, UTC
    val dt_txt : String?,
)

data class FiveDaysForecastResponse (
    //Internal parameter
    val cod : Int?,
    //Internal parameter
    val message : Int?,
    //A number of timestamps returned in the API response
    val cnt : Int?,
    val list : List<Forecast>?,
    val city : City?,
)
