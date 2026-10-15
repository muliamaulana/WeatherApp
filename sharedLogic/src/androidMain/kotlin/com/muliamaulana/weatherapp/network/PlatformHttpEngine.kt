package com.muliamaulana.weatherapp.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun createPlatformHttpEngine(): HttpClientEngine {
    return OkHttp.create()
}
