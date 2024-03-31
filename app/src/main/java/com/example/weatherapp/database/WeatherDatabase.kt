package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [WeatherEntity::class, FavouriteLocation::class, AlarmItem::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao

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

class LocalDateTimeConverter {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        @TypeConverter
        @JvmStatic
        fun toLocalDateTime(value: String?): LocalDateTime? {
            return value?.let {
                return LocalDateTime.parse(it, formatter)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromLocalDateTime(date: LocalDateTime?): String? {
            return date?.format(formatter)
        }
    }
}