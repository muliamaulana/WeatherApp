package com.muliamaulana.weatherapp.domain.usecase

import com.muliamaulana.weatherapp.domain.model.Weather
import com.muliamaulana.weatherapp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Result<Weather> {
        return repository.getForecast(
            latitude = latitude,
            longitude = longitude
        )
    }
}
