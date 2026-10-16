package com.muliamaulana.weatherapp.data.mapper

import com.muliamaulana.weatherapp.data.dto.ForecastResponse
import com.muliamaulana.weatherapp.domain.model.Weather

fun ForecastResponse.toDomain(): Weather {
    return Weather(
        latitude = latitude,
        longitude = longitude,
        temperature = current.temperature,
        weatherCode = current.weatherCode
    )
}
