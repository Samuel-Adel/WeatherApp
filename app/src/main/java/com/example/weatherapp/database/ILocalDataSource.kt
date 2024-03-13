package com.example.weatherapp.database

import com.example.weatherapp.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getSavedWeatherList(): Flow<List<WeatherModel>>

}
