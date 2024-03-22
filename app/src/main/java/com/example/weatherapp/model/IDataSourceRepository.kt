package com.example.weatherapp.model

import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

interface IDataSourceRepository {
    fun getWeatherList(lat: Double, long: Double): Flow<DataSourceState>

}