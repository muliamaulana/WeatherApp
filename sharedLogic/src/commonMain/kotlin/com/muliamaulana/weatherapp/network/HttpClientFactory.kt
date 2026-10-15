package com.muliamaulana.weatherapp.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun createHttpClient(): HttpClient {
        return com.muliamaulana.weatherapp.network.createHttpClient()
    }
}

fun createHttpClient(): HttpClient {
    return HttpClient(
        engine = createPlatformHttpEngine()
    ) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}
