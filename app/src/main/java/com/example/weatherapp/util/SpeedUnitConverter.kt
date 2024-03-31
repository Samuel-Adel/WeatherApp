package com.example.weatherapp.util

import android.content.Context
import com.example.weatherapp.R

enum class SpeedUnit {
    METER_PER_SECOND,
    MILES_PER_HOUR
}

object SpeedUnitConverter {
    fun metersPerSecondToMilesPerHour(speed: Double, unit: SpeedUnit): Double {
        val result = when (unit) {
            SpeedUnit.METER_PER_SECOND -> speed
            SpeedUnit.MILES_PER_HOUR -> speed / 2.23694
        }
        return String.format("%.2f", result).toDouble()
    }

    fun getUnitFromKey(context: Context, key: String?): SpeedUnit {
        return when (key) {
            context.getString(R.string.meter_second_key) -> SpeedUnit.METER_PER_SECOND
            context.getString(R.string.miles_hour_key) -> SpeedUnit.MILES_PER_HOUR
            else -> SpeedUnit.METER_PER_SECOND
        }
    }
}

