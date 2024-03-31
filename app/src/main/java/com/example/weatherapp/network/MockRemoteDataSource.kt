package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRemoteDataSource(private val weatherData: WeatherData? = null) : IRemoteDataSource {

    override fun getWeather(lat: Double, lon: Double): Flow<DataSourceState> {

        return flow {
            emit(DataSourceState.Loading)
            kotlinx.coroutines.delay(1000)
            emit(DataSourceState.Success(weatherData))
        }
    }
}