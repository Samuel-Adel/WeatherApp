package com.example.weatherapp.model


data class UpperClassPojo(
    var weatherList: MutableList<WeatherModel>,
    var total: Int,
    var skip: Int,
    var limit: Int,
)