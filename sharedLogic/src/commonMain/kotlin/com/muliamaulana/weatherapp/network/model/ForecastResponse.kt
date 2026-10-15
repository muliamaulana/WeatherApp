package com.muliamaulana.weatherapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val latitude: Double,
    val longitude: Double,

    @SerialName("current")
    val current: CurrentWeatherDto,

    @SerialName("daily")
    val daily: DailyForecastDto
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m")
    val temperature: Double,

    @SerialName("relative_humidity_2m")
    val relativeHumidity: Int,

    @SerialName("apparent_temperature")
    val apparentTemperature: Double,

    @SerialName("is_day")
    val isDay: Int,

    val precipitation: Double,

    @SerialName("weather_code")
    val weatherCode: Int,

    @SerialName("wind_speed_10m")
    val windSpeed: Double
)

@Serializable
data class DailyForecastDto(
    val time: List<String>,

    @SerialName("weather_code")
    val weatherCodes: List<Int>,

    @SerialName("temperature_2m_max")
    val maxTemperatures: List<Double>,

    @SerialName("temperature_2m_min")
    val minTemperatures: List<Double>
)
