package com.muliamaulana.weatherapp.presentation

import com.muliamaulana.weatherapp.domain.model.Weather
import com.muliamaulana.weatherapp.domain.repository.WeatherRepository
import com.muliamaulana.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads default weather for Jakarta successfully`() = runTest(testDispatcher) {
        val fakeWeather = Weather(
            latitude = -6.2088,
            longitude = 106.8456,
            temperature = 30.5,
            weatherCode = 1
        )
        val repository = FakeWeatherRepository(Result.success(fakeWeather))
        val useCase = GetWeatherUseCase(repository)

        val viewModel = WeatherViewModel(useCase)

        // Initial state before coroutine runs
        assertEquals(WeatherUiState.Loading, viewModel.uiState.value)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<WeatherUiState.Success>(state)
        assertEquals(fakeWeather, state.weather)
    }

    @Test
    fun `init emits Error state when usecase fails`() = runTest(testDispatcher) {
        val errorMessage = "Network timeout"
        val repository = FakeWeatherRepository(Result.failure(RuntimeException(errorMessage)))
        val useCase = GetWeatherUseCase(repository)

        val viewModel = WeatherViewModel(useCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<WeatherUiState.Error>(state)
        assertEquals(errorMessage, state.message)
    }

    @Test
    fun `loadWeather with new coordinates updates state correctly`() = runTest(testDispatcher) {
        val fakeWeather = Weather(
            latitude = -7.2575,
            longitude = 112.7521,
            temperature = 28.0,
            weatherCode = 2
        )
        val repository = FakeWeatherRepository(Result.success(fakeWeather))
        val useCase = GetWeatherUseCase(repository)
        val viewModel = WeatherViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.loadWeather(-7.2575, 112.7521)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertIs<WeatherUiState.Success>(state)
        assertEquals(fakeWeather, state.weather)
    }

    @Test
    fun `WeatherViewModelFactory creates WeatherViewModel correctly`() {
        val repository = FakeWeatherRepository(
            Result.success(Weather(0.0, 0.0, 0.0, 0))
        )
        val useCase = GetWeatherUseCase(repository)
        val factory = WeatherViewModelFactory(useCase)

        val viewModel = factory.create(WeatherViewModel::class.java)
        assertNotNull(viewModel)
    }

    @Test
    fun `WeatherViewModelFactory throws on unknown class`() {
        val repository = FakeWeatherRepository(
            Result.success(Weather(0.0, 0.0, 0.0, 0))
        )
        val useCase = GetWeatherUseCase(repository)
        val factory = WeatherViewModelFactory(useCase)

        class UnknownViewModel : androidx.lifecycle.ViewModel()

        assertFailsWith<IllegalArgumentException> {
            factory.create(UnknownViewModel::class.java)
        }
    }

    private class FakeWeatherRepository(
        private val result: Result<Weather>
    ) : WeatherRepository {
        override suspend fun getForecast(
            latitude: Double,
            longitude: Double
        ): Result<Weather> = result
    }
}
