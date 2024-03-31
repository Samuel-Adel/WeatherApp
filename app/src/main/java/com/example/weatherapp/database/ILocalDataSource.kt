package com.example.weatherapp.database

import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getSavedWeatherList(): Flow<DataSourceState>
    suspend fun saveWeatherData(weatherData: WeatherData)
    fun getFavLocationsList(): Flow<List<FavouriteLocation>>
    suspend fun addFavLocation(favouriteLocation: FavouriteLocation)
    suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation)
    fun geAlarmsList(): Flow<List<AlarmItem>>
    suspend fun addAlarm(alarmItem: AlarmItem)
    suspend fun deleteAlarm(alarmItem: AlarmItem)
}
