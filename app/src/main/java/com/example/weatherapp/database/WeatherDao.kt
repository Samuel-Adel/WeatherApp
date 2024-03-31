package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getWeatherList(): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(weather: WeatherEntity)

    @Delete
    suspend fun deleteProduct(weather: WeatherEntity)

    @Query("SELECT * FROM favourite_location")
    fun getFavouriteLocations(): Flow<List<FavouriteLocation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteLocation(location: FavouriteLocation)

    @Delete
    suspend fun deleteFavouriteLocation(location: FavouriteLocation)

    @Query("SELECT * FROM alarms")
    fun getAlarmsList(): Flow<List<AlarmItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(weather: AlarmItem)

    @Delete
    suspend fun deleteAlarm(weather: AlarmItem)


}