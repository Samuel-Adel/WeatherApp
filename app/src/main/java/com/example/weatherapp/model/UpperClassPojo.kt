package com.example.weatherapp.model


data class UpperClassPojo(
    var weatherList: MutableList<WeatherData>,
    var total: Int,
    var skip: Int,
    var limit: Int,
)