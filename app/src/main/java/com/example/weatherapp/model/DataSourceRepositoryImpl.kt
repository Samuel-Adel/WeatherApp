package com.example.weatherapp.model


import com.example.weatherapp.database.ILocalDataSource
import com.example.weatherapp.network.IRemoteDataSource
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.flow.Flow

class DataSourceRepositoryImpl private constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IDataSourceRepository {
    companion object {
        @Volatile
        private var INSTANCE: DataSourceRepositoryImpl? = null

        fun getInstance(
            localDataSource: ILocalDataSource,
            remoteDataSource: IRemoteDataSource
        ): DataSourceRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                val instance = DataSourceRepositoryImpl(localDataSource, remoteDataSource)
                INSTANCE = instance
                instance
            }
        }
    }

    override fun getWeatherList(): Flow<DataSourceState> {
        return remoteDataSource.getWeather()
    }


}