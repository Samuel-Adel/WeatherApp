package com.example.weatherapp.database

import com.example.weatherapp.model.WeatherModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherModel::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getProductDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(context) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, WeatherDatabase::class.java, "products_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}