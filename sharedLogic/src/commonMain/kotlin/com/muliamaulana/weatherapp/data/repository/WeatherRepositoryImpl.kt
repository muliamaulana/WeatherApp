package com.muliamaulana.weatherapp.data.repository

import com.muliamaulana.weatherapp.data.mapper.toDomain
import com.muliamaulana.weatherapp.data.network.WeatherApiClient
import com.muliamaulana.weatherapp.domain.model.Weather
import com.muliamaulana.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApiClient
) : WeatherRepository {

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Result<Weather> {
        return try {
            Result.success(
                api.fetchForecast(
                    latitude = latitude,
                    longitude = longitude
                ).toDomain()
            )
        } catch (exception: kotlinx.coroutines.CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
