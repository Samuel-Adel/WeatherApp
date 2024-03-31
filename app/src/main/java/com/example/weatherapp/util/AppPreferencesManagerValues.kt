package com.example.weatherapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.weatherapp.R

object AppPreferencesManagerValues {

    private const val PREFS_NAME = "MyPreferences"
    private const val KEY_DOUBLE_1 = "double1"
    private const val KEY_DOUBLE_2 = "double2"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefs: SharedPreferences

    var language: String? = null
    var location: String? = null
    var tempUnit: String? = null
    var windSpeed: String? = null
    var notificationStatus: Boolean? = null
    fun prefsSetup(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        language = sharedPreferences.getString(context.getString(R.string.language_key), "en")
        tempUnit = sharedPreferences.getString(context.getString(R.string.temp_unit_key), "en")
        location = sharedPreferences.getString(context.getString(R.string.location_key), "gps_key")
        windSpeed = sharedPreferences.getString(
            context.getString(R.string.speed_unit_key),
            "meter_second_key"
        )
        Log.i("Prefs", "prefsSetup: first " + language)
        Log.i("Prefs", "prefsSetup: temp " + tempUnit)
    }

    fun saveLonLat(double1: Double, double2: Double) {
        val editor = prefs.edit()
        editor.putFloat(KEY_DOUBLE_1, double1.toFloat())
        editor.putFloat(KEY_DOUBLE_2, double2.toFloat())
        editor.apply()
    }

    fun getLon(): Double {
        return prefs.getFloat(KEY_DOUBLE_1, 31.2357f).toDouble()
    }

    fun getLat(): Double {
        return prefs.getFloat(KEY_DOUBLE_2, 30.0444f).toDouble()
    }
}