package com.muliamaulana.weatherapp.domain.model

data class DailyForecast(
    val date: String,
    val weatherCode: Int,
    val temperatureMax: Double,
    val temperatureMin: Double
)
