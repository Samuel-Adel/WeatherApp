package com.example.weatherapp.network

import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
     fun getWeather(lat: Double, long: Double): Flow<DataSourceState>
}