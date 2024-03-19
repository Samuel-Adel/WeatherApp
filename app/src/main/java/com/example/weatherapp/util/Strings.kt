package com.example.weatherapp.util

fun String.addDegreeSymbol(degree: Double, temperatureUnit: TemperatureUnit): String {
    val temp: Temperature = Temperature(degree, temperatureUnit)
    val tempDegree = temp.convertTo(temperatureUnit)
    return when (temperatureUnit) {
        TemperatureUnit.CELSIUS -> {
            "$degree°C"
        }

        TemperatureUnit.FAHRENHEIT -> {
            "$degree°F"
        }
    }

}