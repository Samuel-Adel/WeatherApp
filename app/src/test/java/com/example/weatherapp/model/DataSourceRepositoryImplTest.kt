package com.example.weatherapp.model

import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DataSourceRepositoryImplTest {

    private lateinit var localDataSource: MockLocalDataSource
    private lateinit var remoteDataSource: MockRemoteDataSource
    private lateinit var repository: DataSourceRepositoryImpl
    val weatherData = WeatherData(
        40.7128,
        -74.0060,
        "America/New_York",
        -14400,
        CurrentWeather(
            System.currentTimeMillis() / 1000,
            System.currentTimeMillis() / 1000,
            System.currentTimeMillis() / 1000,
            20.5,
            19.0,
            1010,
            60,
            10.0,
            5.0,
            20,
            10000,
            5.0,
            180,
            7.0,
            listOf(
                WeatherCondition(
                    800,
                    "Clear",
                    "clear sky",
                    "01d"
                )
            )
        ),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )

    val favLocationsList = mutableListOf(
        FavouriteLocation(
            lat = 40.73061,
            lon = -73.935242,
            name = "New York City"
        ),
        FavouriteLocation(
            lat = 34.052235,
            lon = -118.243683,
            name = "Los Angeles"
        )
    )

    val alarmsList = mutableListOf(
        AlarmItem(
            time = LocalDateTime.now(),
            longitude = 34.052235,
            latitude = -118.243683
        ),
        AlarmItem(
            time = LocalDateTime.now(),
            longitude = 34.052235,
            latitude = -118.243683
        )
    )

    @Before
    fun setUp() {

        localDataSource = MockLocalDataSource(weatherData, favLocationsList, alarmsList)
        remoteDataSource = MockRemoteDataSource()
        repository = DataSourceRepositoryImpl.getInstance(localDataSource, remoteDataSource)
    }

    @Test
    fun testGetSavedWeatherList() = runBlocking {
        val resultFlow = repository.getSavedWeatherList()
        if (resultFlow is DataSourceState.Success<*>) {
            assertThat(resultFlow.data, `is`(weatherData))
        }
    }

    @Test
    fun testGetFavLocationsList() = runBlocking {
        val resultList = repository.getFavLocationsList().first()
        val result = resultList.first()
        assertThat(result, `is`(localDataSource.favLocationsList.first()))
    }

    @Test
    fun testGetAlarmsList() = runBlocking {
        val resultList = repository.getAlarmsList().first()
        val result = resultList.first()
        assertThat(result, `is`(localDataSource.alarmsList.first()))
    }
}