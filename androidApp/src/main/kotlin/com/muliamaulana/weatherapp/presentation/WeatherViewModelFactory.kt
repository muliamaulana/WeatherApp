package com.muliamaulana.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muliamaulana.weatherapp.data.network.WeatherApiClient
import com.muliamaulana.weatherapp.data.repository.WeatherRepositoryImpl
import com.muliamaulana.weatherapp.domain.usecase.GetWeatherUseCase
import com.muliamaulana.weatherapp.network.HttpClientFactory

class WeatherViewModelFactory(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(
                WeatherViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(
                getWeatherUseCase
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }

    companion object {
        private val httpClient by lazy { HttpClientFactory.createHttpClient() }
        private val weatherApiClient by lazy { WeatherApiClient(httpClient) }
        private val weatherRepository by lazy { WeatherRepositoryImpl(weatherApiClient) }
        val getWeatherUseCase by lazy { GetWeatherUseCase(weatherRepository) }

        fun create(): WeatherViewModelFactory {
            return WeatherViewModelFactory(getWeatherUseCase)
        }
    }
}
