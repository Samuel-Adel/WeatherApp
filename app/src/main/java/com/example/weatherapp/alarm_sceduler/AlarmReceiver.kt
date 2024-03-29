package com.example.weatherapp.alarm_sceduler

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 123
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("Extra_Message") ?: return
        Log.i("Alarm", "onReceive: $message")
        showNotification(context, message)
    }

    private fun showNotification(context: Context?, message: String) {
        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = createNotificationChanel(context, message)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

    }
}