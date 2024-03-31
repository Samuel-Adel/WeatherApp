package com.example.weatherapp.model

import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

interface IDataSourceRepository {
    fun getWeatherList(lat: Double, long: Double): Flow<DataSourceState>
    fun getSavedWeatherList(): Flow<DataSourceState>
    fun getFavLocationsList(): Flow<List<FavouriteLocation>>
    suspend fun addFavLocation(favouriteLocation: FavouriteLocation)
    suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation)

    fun getAlarmsList(): Flow<List<AlarmItem>>
    suspend fun addAlarm(alarmItem: AlarmItem)
    suspend fun saveWeatherData(weatherData: WeatherData)
    suspend fun deleteAlarm(alarmItem: AlarmItem)

}