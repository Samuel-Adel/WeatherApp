package com.example.weatherapp.alarm_sceduler

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.DataSourceRepositoryImpl
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.example.weatherapp.util.DataSourceState
import com.example.weatherapp.util.WeatherHandlingHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 123
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarm = intent?.getSerializableExtra("myObject") as AlarmItem
        context.let {
            CoroutineScope(Dispatchers.IO).launch {
                val repo = DataSourceRepositoryImpl.getInstance(
                    LocalDataSourceImpl.getInstance(context!!), RemoteDataSourceImpl.getInstance()
                )
                repo.getWeatherList(alarm.latitude, alarm.longitude).collectLatest { result ->
                    if (result is DataSourceState.Success<*>) {
                        if (result.data is WeatherData) {
                            WeatherHandlingHelper.sunrise = result.data.current.sunrise
                            WeatherHandlingHelper.sunset = result.data.current.sunset
                            showNotification(
                                context, result.data.hourly.first().weather.first().description
                            )

                        }
                    } else if (result is DataSourceState.Failure) {
                        Log.i("WeatherResponse", result.msg.message.toString())
                        Toast.makeText(
                            context,
                            R.string.this_location_does_not_contain_data,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }
        }
    }

    private fun showNotification(context: Context?, message: String) {
        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = createNotificationChanel(context, message)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

    }
}