package com.example.weatherapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.weatherapp.R

object AppPreferencesManagerValues {
    lateinit var sharedPreferences: SharedPreferences
    var language: String? = null
    var location: String? = null
    var tempUnit: String? = null
    var windSpeed: String? = null
    var notificationStatus: Boolean? = null
    fun prefsSetup(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        language = sharedPreferences.getString(context.getString(R.string.language_key), "en")
        tempUnit = sharedPreferences.getString(context.getString(R.string.temp_unit_key), "en")
        Log.i("Prefs", "prefsSetup: first " + language)
        Log.i("Prefs", "prefsSetup: temp " + tempUnit)
    }

    fun getAppTempUnit(context: Context) {
        sharedPreferences.getString(context.getString(R.string.temp_unit_key), "")
    }

    fun getAppSpeedUnit(context: Context) {

        sharedPreferences.getString(context.getString(R.string.speed_unit), "")
    }

    fun getNotificationStatus(context: Context) {
        sharedPreferences.getBoolean(context.getString(R.string.notification), false)
    }
}