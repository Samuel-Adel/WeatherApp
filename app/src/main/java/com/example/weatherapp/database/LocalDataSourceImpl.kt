package com.example.weatherapp.database
import android.content.Context
import com.example.weatherapp.model.WeatherModel

import kotlinx.coroutines.flow.Flow

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
        WeatherDatabase.getInstance(context).getProductDao()
    }

    override  fun getSavedWeatherList(): Flow<List<WeatherModel>> {
        return dao.getWeatherList()
    }



}