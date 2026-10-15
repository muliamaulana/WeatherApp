package com.muliamaulana.weatherapp.network

import com.muliamaulana.weatherapp.network.model.ForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiClient(
    private val httpClient: HttpClient
) {

    suspend fun fetchForecast(
        latitude: Double,
        longitude: Double
    ): ForecastResponse {
        return try {
            httpClient.get(
                "https://api.open-meteo.com/v1/forecast"
            ) {
                parameter("latitude", latitude)
                parameter("longitude", longitude)

                parameter(
                    "current",
                    "temperature_2m," +
                        "relative_humidity_2m," +
                        "apparent_temperature," +
                        "is_day," +
                        "precipitation," +
                        "weather_code," +
                        "wind_speed_10m"
                )

                parameter(
                    "daily",
                    "weather_code," +
                        "temperature_2m_max," +
                        "temperature_2m_min"
                )

                parameter("forecast_days", 7)
                parameter("timezone", "auto")
            }.body()
        } catch (e: ClientRequestException) {
            throw IllegalStateException(
                "Request ditolak oleh server: ${e.response.status}"
            )
        } catch (e: ServerResponseException) {
            throw IllegalStateException(
                "Server mengalami masalah: ${e.response.status}"
            )
        }
    }
}
