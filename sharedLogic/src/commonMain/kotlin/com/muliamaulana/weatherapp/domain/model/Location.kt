package com.muliamaulana.weatherapp.domain.model

data class Location(
    val id: Long?,
    val name: String,
    val country: String,
    val adminArea: String?,
    val latitude: Double,
    val longitude: Double
)
