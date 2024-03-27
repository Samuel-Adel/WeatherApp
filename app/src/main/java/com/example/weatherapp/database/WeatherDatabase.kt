package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherEntity

@Database(entities = [WeatherEntity::class, FavouriteLocation::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getProductDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(context) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, WeatherDatabase::class.java, "weather"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}