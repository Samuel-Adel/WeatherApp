package com.example.weatherapp.alarm_screen.viewModel

import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.IAlarmSchedulerRepository

class FakeAlarmSchedulerRepository : IAlarmSchedulerRepository {

    private val scheduledAlarms: MutableSet<AlarmItem> = mutableSetOf()

    override fun schedule(item: AlarmItem) {
        scheduledAlarms.add(item)
    }

    override fun cancel(item: AlarmItem) {
        scheduledAlarms.remove(item)
    }

    // Helper function to check if an alarm is scheduled
    fun isAlarmScheduled(item: AlarmItem): Boolean {
        return scheduledAlarms.contains(item)
    }
}
