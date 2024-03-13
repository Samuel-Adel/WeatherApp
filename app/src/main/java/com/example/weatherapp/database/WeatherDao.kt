package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.WeatherModel
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getWeatherList(): Flow<List<WeatherModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(products: WeatherModel)

    @Delete
    suspend fun deleteProduct(products: WeatherModel)
}