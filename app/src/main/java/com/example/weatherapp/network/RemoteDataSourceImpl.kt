package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl : IRemoteDataSource {
    override fun getWeather(): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)
        val response = API.retrofitService.getWeather()
        emit(DataSourceState.Success<List<WeatherModel>>(response.weatherList))
    }
}