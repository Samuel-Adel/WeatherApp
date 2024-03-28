package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_location")
data class FavouriteLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "latitude")
    val lat: Double,
    @ColumnInfo(name = "longitude")
    val lon: Double,
    @ColumnInfo(name = "name")
    val name: String
)