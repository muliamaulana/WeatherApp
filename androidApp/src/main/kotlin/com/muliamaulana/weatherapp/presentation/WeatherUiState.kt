package com.muliamaulana.weatherapp.presentation

import com.muliamaulana.weatherapp.domain.model.Weather

sealed interface WeatherUiState {

    data object Loading : WeatherUiState

    data class Success(
        val weather: Weather
    ) : WeatherUiState

    data class Error(
        val message: String
    ) : WeatherUiState
}
