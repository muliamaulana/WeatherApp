package com.muliamaulana.weatherapp.domain.model

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val weatherCode: Int
)
