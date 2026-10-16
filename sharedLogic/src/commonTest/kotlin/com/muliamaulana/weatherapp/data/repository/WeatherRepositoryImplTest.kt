package com.muliamaulana.weatherapp.data.repository

import com.muliamaulana.weatherapp.data.network.WeatherApiClient
import com.muliamaulana.weatherapp.domain.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeatherRepositoryImplTest {

    @Test
    fun `getForecast returns mapped weather`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """
                    {
                      "latitude": -6.2,
                      "longitude": 106.8,
                      "current": {
                        "temperature_2m": 30.5,
                        "weather_code": 1
                      }
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    "application/json"
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        try {
            val api = WeatherApiClient(httpClient)
            val repository = WeatherRepositoryImpl(api)

            val result = repository.getForecast(
                latitude = -6.2,
                longitude = 106.8
            )

            assertTrue(result.isSuccess)

            assertEquals(
                Weather(
                    latitude = -6.2,
                    longitude = 106.8,
                    temperature = 30.5,
                    weatherCode = 1
                ),
                result.getOrThrow()
            )
        } finally {
            httpClient.close()
        }
    }

    @Test
    fun `getForecast returns failure on server error`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"error": true}""",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    "application/json"
                )
            )
        }

        val httpClient = HttpClient(mockEngine) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        try {
            val api = WeatherApiClient(httpClient)
            val repository = WeatherRepositoryImpl(api)

            val result = repository.getForecast(
                latitude = -6.2,
                longitude = 106.8
            )

            assertTrue(result.isFailure)
        } finally {
            httpClient.close()
        }
    }
}
