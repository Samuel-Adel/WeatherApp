package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val brand: String,
    val category: String,
    val thumbnail: String,
)