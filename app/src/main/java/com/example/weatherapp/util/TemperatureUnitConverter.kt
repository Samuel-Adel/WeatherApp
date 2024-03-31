package com.example.weatherapp.util

import android.content.Context
import com.example.weatherapp.R

enum class TemperatureUnit {
    CELSIUS, FAHRENHEIT, KELVIN
}

object Temperature {

    fun convertTo(
        value: Double,
        currentUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
        context: Context,
        targetUnitKey: String?
    ): Double {
        return convertValue(value, currentUnit, getUnitFromKey(context, targetUnitKey))

    }

    private fun convertValue(
        value: Double, fromUnit: TemperatureUnit, toUnit: TemperatureUnit
    ): Double {
        return when (fromUnit) {
            TemperatureUnit.CELSIUS -> {
                when (toUnit) {
                    TemperatureUnit.CELSIUS -> value
                    TemperatureUnit.FAHRENHEIT -> (value * 9 / 5) + 32
                    TemperatureUnit.KELVIN -> value + 273.15
                }
            }

            TemperatureUnit.FAHRENHEIT -> {
                when (toUnit) {
                    TemperatureUnit.CELSIUS -> (value - 32) * 5 / 9
                    TemperatureUnit.FAHRENHEIT -> value
                    TemperatureUnit.KELVIN -> (value + 459.67) * 5 / 9
                }
            }

            TemperatureUnit.KELVIN -> {
                when (toUnit) {
                    TemperatureUnit.CELSIUS -> value - 273.15
                    TemperatureUnit.FAHRENHEIT -> (value * 9 / 5) - 459.67
                    TemperatureUnit.KELVIN -> value
                }
            }
        }
    }

     fun getUnitFromKey(context: Context, key: String?): TemperatureUnit {
        return when (key) {
            context.getString(R.string.celsius_key) -> TemperatureUnit.CELSIUS
            context.getString(R.string.fahrenheit_key) -> TemperatureUnit.FAHRENHEIT
            context.getString(R.string.kelvin_key) -> TemperatureUnit.KELVIN
            else -> TemperatureUnit.CELSIUS
        }
    }
}
