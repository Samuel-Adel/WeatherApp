package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "alarms")
data class AlarmItem(
    @PrimaryKey
    @ColumnInfo(name = "time")
    val time: LocalDateTime,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")

    val longitude: Double
) : Serializable