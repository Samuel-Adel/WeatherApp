package com.example.weatherapp.network

import android.util.Log
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl private constructor() : IRemoteDataSource {
    companion object {
        @Volatile
        private var INSTANCE: RemoteDataSourceImpl? = null
        fun getInstance(): RemoteDataSourceImpl {
            return INSTANCE ?: synchronized(this) {
                val instance = RemoteDataSourceImpl()
                INSTANCE = instance
                instance
            }
        }
    }

    override fun getWeather(lat: Double, long: Double): Flow<DataSourceState> = flow {
        emit(DataSourceState.Loading)
        try {
            val response = API.retrofitService.getWeather(30.0444, 31.2357)
            if (response.isSuccessful) {
                emit(DataSourceState.Success(response.body()))
                Log.i("WeatherResponse", "getWeather: " + response.body()?.latitude)
                Log.i("WeatherResponse", "getWeather: " + response.body()?.longitude)
                Log.i("WeatherResponse", "getWeather: " + response.body())
            } else {
                emit(DataSourceState.Failure(Throwable("This Location doesn't contain data for Weather!")))
            }
            Log.i("WeatherResponse", "getWeather: " + response.isSuccessful)
        } catch (e: Exception) {
            emit(DataSourceState.Failure(e))
        }
    }
}