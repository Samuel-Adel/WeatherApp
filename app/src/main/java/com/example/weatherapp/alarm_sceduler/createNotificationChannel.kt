package com.example.weatherapp.alarm_sceduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R

private const val CHANNEL_ID = "notificationChannel"

fun createNotificationChanel(context: Context, message: String): NotificationCompat.Builder {
    val serviceChannel = NotificationChannel(
        CHANNEL_ID,
        "Foreground Service Channel",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    val manager =
        context.getSystemService(NotificationManager::class.java) as NotificationManager
    manager.createNotificationChannel(serviceChannel)
    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Weather Alarm")
        .setContentText(message)
        .setSmallIcon(R.drawable.light_foggy)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

}
