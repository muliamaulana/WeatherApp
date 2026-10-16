package com.muliamaulana.weatherapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeatherDto,
    val daily: DailyForecastDto? = null
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m")
    val temperature: Double,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("relative_humidity_2m")
    val relativeHumidity: Int = 0,
    @SerialName("apparent_temperature")
    val apparentTemperature: Double = 0.0,
    @SerialName("is_day")
    val isDay: Int = 1,
    val precipitation: Double = 0.0,
    @SerialName("wind_speed_10m")
    val windSpeed: Double = 0.0
)

@Serializable
data class DailyForecastDto(
    val time: List<String> = emptyList(),
    @SerialName("weather_code")
    val weatherCodes: List<Int> = emptyList(),
    @SerialName("temperature_2m_max")
    val maxTemperatures: List<Double> = emptyList(),
    @SerialName("temperature_2m_min")
    val minTemperatures: List<Double> = emptyList()
)
