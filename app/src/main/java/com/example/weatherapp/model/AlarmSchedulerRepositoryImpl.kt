package com.example.weatherapp.model


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.weatherapp.alarm_sceduler.AlarmReceiver
import java.time.ZoneId

class AlarmSchedulerRepositoryImpl(private val context: Context) : IAlarmSchedulerRepository {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(item: AlarmItem) {
        Log.i("hashCode", "add: " + item.time.hashCode() + " " + item.hashCode())
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("Extra_Message", item)
        }
        Log.i("AlarmDetails", "schedule: " + item.time + " -> ")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.time.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: AlarmItem) {
        Log.i("hashCode", "cancel: " + item.time.hashCode() + " " + item.hashCode())
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.time.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}