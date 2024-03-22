package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl : IRemoteDataSource {
    override fun getWeather(lat: Double, long: Double): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)
        val response = API.retrofitService.getWeather(lat, long, "", "")
        emit(DataSourceState.Success<List<WeatherData>>(response.weatherList))
    }
}