package com.example.weatherapp.util


enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}

class Temperature(private val value: Double, private val unit: TemperatureUnit) {

    fun convertTo(targetUnit: TemperatureUnit): Temperature {
        val newValue = convertValue(value, unit, targetUnit)
        return Temperature(newValue, targetUnit)
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

