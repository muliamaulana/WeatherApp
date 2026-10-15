package com.muliamaulana.weatherapp.domain.model

data class CurrentWeather(
    val temperature: Double,
    val apparentTemperature: Double,
    val relativeHumidity: Int,
    val isDay: Boolean,
    val precipitation: Double,
    val weatherCode: Int,
    val windSpeed: Double
)
