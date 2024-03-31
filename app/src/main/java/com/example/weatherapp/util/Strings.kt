package com.example.weatherapp.util

fun String.addDegreeSymbol(temperatureUnit: TemperatureUnit): String {
    val symbol = when (temperatureUnit) {
        TemperatureUnit.CELSIUS -> "°C"
        TemperatureUnit.FAHRENHEIT -> "°F"
        else -> {
            "K"
        }
    }
    return "$this$symbol"
}

fun String.addSpeedUnit(speedUnit: SpeedUnit): String {
    val unit = when (speedUnit) {
        SpeedUnit.METER_PER_SECOND -> "m/s"
        SpeedUnit.MILES_PER_HOUR -> "mi/h"
    }
    return "$this $unit"
}