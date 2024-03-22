package com.example.weatherapp.database

import com.example.weatherapp.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getSavedWeatherList(): Flow<List<WeatherData>>

}
