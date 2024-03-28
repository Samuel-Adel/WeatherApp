package com.example.weatherapp.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.example.weatherapp.model.DailyData
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.TemperatureData
import java.util.*

@BindingAdapter("timestampToDayOfWeek")
fun getDayOfWeek(textView: TextView, timestamp: Long) {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = timestamp * 1000 // Convert timestamp to milliseconds
    }

    val dayName = when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sun"
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tue"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        else -> "Unknown"
    }
    textView.text = dayName
}

@BindingAdapter("minMaxTempFormat")

fun formatTemperature(textView: TextView, temp: TemperatureData) {
    textView.text = "${temp.max.toInt()}/${temp.min.toInt()}°"
}

@BindingAdapter("weatherMainToImgFromDailyWeather")
fun getWeatherImage(img: ImageView, weatherModel: DailyData) {
    Log.i("WeatheerSa", "getWeatherImage: ")
    val currentTime = weatherModel.timestamp
    val sunriseTime = weatherModel.sunrise
    val sunsetTime = weatherModel.sunset

    val isDayTime = currentTime in sunriseTime until sunsetTime

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
    img.setImageResource(imgSelected)
}
