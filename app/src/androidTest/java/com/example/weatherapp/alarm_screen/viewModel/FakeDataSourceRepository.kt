package com.example.weatherapp.alarm_screen.viewModel

import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.IDataSourceRepository
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSourceRepository : IDataSourceRepository {

    private val fakeWeatherList: MutableList<WeatherData> = mutableListOf()
    private val fakeFavLocationsList: MutableList<FavouriteLocation> = mutableListOf()
    private val fakeAlarmsList: MutableList<AlarmItem> = mutableListOf()

    override fun getWeatherList(lat: Double, long: Double): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)
    }

    override fun getSavedWeatherList(): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)

        if (fakeWeatherList.isNotEmpty()) {
            val fakeWeatherData = fakeWeatherList.first()
            emit(DataSourceState.Success(fakeWeatherData))
        } else {
            emit(DataSourceState.Failure(Exception("No weather data available")))
        }
    }

    override fun getFavLocationsList(): Flow<List<FavouriteLocation>> = flow {

        emit(fakeFavLocationsList)
    }

    override suspend fun addFavLocation(favouriteLocation: FavouriteLocation) {
        fakeFavLocationsList.add(favouriteLocation)
    }

    override suspend fun deleteFavLocation(favouriteLocation: FavouriteLocation) {
        fakeFavLocationsList.remove(favouriteLocation)
    }

    override fun getAlarmsList(): Flow<List<AlarmItem>> = flow {
        // Simulate loading state

        // Simulate success state with fake data
        emit(fakeAlarmsList)
    }

    override suspend fun addAlarm(alarmItem: AlarmItem) {
        fakeAlarmsList.add(alarmItem)
    }

    override suspend fun saveWeatherData(weatherData: WeatherData) {
        fakeWeatherList.clear()
        fakeWeatherList.add(weatherData)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        fakeAlarmsList.remove(alarmItem)
    }
}
