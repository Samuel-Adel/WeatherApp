package com.example.weatherapp.util


enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}

object Temperature {

    fun convertTo(
        value: Double,
        currentUnit: TemperatureUnit,
        targetUnit: TemperatureUnit
    ): String {
        return convertValue(value, currentUnit, targetUnit).toString()
    }

    private fun convertValue(
        value: Double,
        fromUnit: TemperatureUnit,
        toUnit: TemperatureUnit
    ): Double {
        return when (fromUnit) {
            TemperatureUnit.CELSIUS -> {
                when (toUnit) {
                    TemperatureUnit.CELSIUS -> value
                    TemperatureUnit.FAHRENHEIT -> (value * 9 / 5) + 32
                }
            }

            TemperatureUnit.FAHRENHEIT -> {
                when (toUnit) {
                    TemperatureUnit.CELSIUS -> (value - 32) * 5 / 9
                    TemperatureUnit.FAHRENHEIT -> value
                }
            }
        }
    }
}

