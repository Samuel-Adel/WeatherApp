package com.example.weatherapp.util

import android.util.Log
import android.widget.ImageView
import com.example.weatherapp.R
import com.example.weatherapp.model.CurrentWeather

object WeatherHandlingHelper {
    var sunrise: Long? = null
    var sunset: Long? = null
    fun getWeatherImage(weatherModel: CurrentWeather):Int {
        Log.i("WeatheerSa", "getWeatherImage: ")
        val currentTime = weatherModel.timestamp
        val isDayTime = currentTime in sunrise!! until sunset!!
        val weatherMain = weatherModel.weather.first().main
        val imgSelected = when {
            isDayTime -> {
                when (weatherMain) {
                    "Rain" -> {
                        when {
                            weatherModel.weather.first().description.contains(
                                "heavy",
                                ignoreCase = true
                            ) -> R.drawable.light_heavy_rain

                            else -> R.drawable.light_mid_rain
                        }
                    }

                    "Clouds" -> R.drawable.light_partially_cloudy
                    "Clear" -> R.drawable.light_clear
                    "Snow" -> R.drawable.snow
                    "Drizzle" -> R.drawable.drizzle
                    "Thunderstorm" -> R.drawable.thunder_storm
                    else -> R.drawable.light_foggy
                }
            }

            else -> {
                when (weatherMain) {
                    "Rain" -> {
                        when {
                            weatherModel.weather.first().description.contains(
                                "heavy",
                                ignoreCase = true
                            ) -> R.drawable.night_heavy_rain

                            else -> R.drawable.night_mid_rain
                        }
                    }

                    "Clouds" -> R.drawable.night_partialy_cloudy
                    "Clear" -> R.drawable.clear
                    "Snow" -> R.drawable.snow
                    "Drizzle" -> R.drawable.drizzle
                    "Thunderstorm" -> R.drawable.thunder_storm
                    else -> R.drawable.night_foggy
                }
            }
        }
        return imgSelected
    }
}