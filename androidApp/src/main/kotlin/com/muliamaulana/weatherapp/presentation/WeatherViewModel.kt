package com.muliamaulana.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muliamaulana.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<WeatherUiState>(
            WeatherUiState.Loading
        )

    val uiState: StateFlow<WeatherUiState> =
        _uiState.asStateFlow()

    init {
        loadWeather(
            latitude = -6.2088,
            longitude = 106.8456
        )
    }

    fun loadWeather(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            getWeatherUseCase(
                latitude = latitude,
                longitude = longitude
            ).onSuccess { weather ->
                _uiState.value = WeatherUiState.Success(weather)
            }.onFailure { error ->
                _uiState.value = WeatherUiState.Error(
                    message = error.message ?: "Terjadi kesalahan saat memuat data"
                )
            }
        }
    }
}
