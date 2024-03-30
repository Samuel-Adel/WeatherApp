package com.example.weatherapp.model

import com.example.weatherapp.model.AlarmItem

interface IAlarmSchedulerRepository {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}