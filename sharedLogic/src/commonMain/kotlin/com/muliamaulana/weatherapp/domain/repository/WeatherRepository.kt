package com.muliamaulana.weatherapp.domain.repository

import com.muliamaulana.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Result<Weather>
}
