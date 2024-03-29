package com.example.weatherapp.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String
)