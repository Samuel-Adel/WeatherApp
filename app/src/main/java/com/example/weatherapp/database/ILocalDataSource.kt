package com.example.weatherapp.database

import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getSavedWeatherList(): Flow<List<WeatherData>>
    fun getFavLocationsList(): Flow<List<FavouriteLocation>>
    suspend fun addFavLocation(favouriteLocation: FavouriteLocation)
    suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation)
    fun geAlarmsList(): Flow<List<AlarmItem>>
    suspend fun addAlarm(alarmItem: AlarmItem)
    suspend fun deleteAlarm(alarmItem: AlarmItem)
}
