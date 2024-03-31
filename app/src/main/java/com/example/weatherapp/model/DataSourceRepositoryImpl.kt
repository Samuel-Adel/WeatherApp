package com.example.weatherapp.model

import com.example.weatherapp.database.ILocalDataSource
import com.example.weatherapp.network.IRemoteDataSource
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

class DataSourceRepositoryImpl private constructor(
    private val localDataSource: ILocalDataSource, private val remoteDataSource: IRemoteDataSource
) : IDataSourceRepository {
    companion object {
        @Volatile
        private var INSTANCE: DataSourceRepositoryImpl? = null
        fun getInstance(
            localDataSource: ILocalDataSource, remoteDataSource: IRemoteDataSource
        ): DataSourceRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                val instance = DataSourceRepositoryImpl(localDataSource, remoteDataSource)
                INSTANCE = instance
                instance
            }
        }
    }

    override fun getWeatherList(lat: Double, long: Double): Flow<DataSourceState> {
        return remoteDataSource.getWeather(lat, long)
    }

    override fun getSavedWeatherList(): Flow<DataSourceState> {
        return localDataSource.getSavedWeatherList()
    }

    override fun getFavLocationsList(): Flow<List<FavouriteLocation>> {
        return localDataSource.getFavLocationsList()
    }

    override suspend fun addFavLocation(favouriteLocation: FavouriteLocation) {
        localDataSource.addFavLocation(favouriteLocation)
    }

    override suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation) {
        localDataSource.deleteFavLocation(favouriteLocation)
    }

    override fun getAlarmsList(): Flow<List<AlarmItem>> {
        return localDataSource.geAlarmsList()
    }

    override suspend fun addAlarm(alarmItem: AlarmItem) {
        localDataSource.addAlarm(alarmItem)
    }

    override suspend fun saveWeatherData(weatherData: WeatherData) {
        localDataSource.saveWeatherData(weatherData)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        localDataSource.deleteAlarm(alarmItem)
    }


}