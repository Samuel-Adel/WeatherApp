package com.example.weatherapp.model
import com.example.weatherapp.database.ILocalDataSource
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockLocalDataSource(
     val savedWeatherList: WeatherData,
     val favLocationsList: MutableList<FavouriteLocation> = mutableListOf(),
     val alarmsList: MutableList<AlarmItem> = mutableListOf()
) : ILocalDataSource {
    override fun getSavedWeatherList(): Flow<DataSourceState> {
        return flow {
            emit(DataSourceState.Success(savedWeatherList))
        }
    }

    override suspend fun saveWeatherData(weatherData: WeatherData) {

    }

    override fun getFavLocationsList(): Flow<List<FavouriteLocation>> {
        return flow {
            emit(favLocationsList)
        }
    }

    override suspend fun addFavLocation(favouriteLocation: FavouriteLocation) {

    }

    override suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation) {

    }

    override fun geAlarmsList(): Flow<List<AlarmItem>> {
        return flow {
            emit(alarmsList)
        }
    }

    override suspend fun addAlarm(alarmItem: AlarmItem) {
        alarmsList.add(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        alarmsList.remove(alarmItem)
    }
}