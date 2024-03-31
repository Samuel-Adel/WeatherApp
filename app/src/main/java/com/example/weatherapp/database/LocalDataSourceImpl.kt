package com.example.weatherapp.database

import android.content.Context
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.WeatherEntity
import com.example.weatherapp.util.DataSourceState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class LocalDataSourceImpl private constructor(context: Context) : ILocalDataSource {

    companion object {
        @Volatile
        private var instance: LocalDataSourceImpl? = null

        fun getInstance(context: Context): LocalDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSourceImpl(context).also { instance = it }
            }
        }
    }

    private val dao: WeatherDao by lazy {
        WeatherDatabase.getInstance(context).getWeatherDao()
    }

    override fun getSavedWeatherList(): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)
        try {
            val weatherEntity = dao.getWeatherList().firstOrNull()
            val weatherData = weatherEntity?.let {
                Gson().fromJson(it.json, WeatherData::class.java)
            }
            if (weatherData != null) {
                emit(DataSourceState.Success(weatherData))
            } else {
                emit(DataSourceState.Failure(Exception("No weather data available")))
            }
        } catch (e: Exception) {
            emit(DataSourceState.Failure(e))
        }
    }

    override suspend fun saveWeatherData(weatherData: WeatherData) {
        val json = Gson().toJson(weatherData)
        val weatherEntity = WeatherEntity(id = 0, json = json)
        dao.insertWeatherData(weatherEntity)
    }

    override fun getFavLocationsList(): Flow<List<FavouriteLocation>> {
        return dao.getFavouriteLocations()
    }

    override suspend fun addFavLocation(favouriteLocation: FavouriteLocation) {
        dao.insertFavouriteLocation(favouriteLocation)
    }

    override suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation) {
        dao.deleteFavouriteLocation(favouriteLocation)
    }

    override fun geAlarmsList(): Flow<List<AlarmItem>> {
        return dao.getAlarmsList()
    }

    override suspend fun addAlarm(alarmItem: AlarmItem) {
        dao.insertAlarm(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        dao.deleteAlarm(alarmItem)
    }


}