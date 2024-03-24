package com.example.weatherapp.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.example.weatherapp.model.HourlyWeather
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("timestampToHour")
fun convertTimestampToHour(textView: TextView, timestamp: Long) {
    val date = Date(timestamp * 1000L)
    val sdf =
        SimpleDateFormat("h a", Locale.getDefault()) // "h a" for 12-hour format with AM/PM
    textView.text = sdf.format(date)
}

@BindingAdapter("weatherMainToImg")
fun getWeatherImage(img: ImageView, weatherModel: HourlyWeather) {
    Log.i("WeatheerSa", "getWeatherImage: ")
    val currentTime = weatherModel.timestamp
    val sunriseTime = SunriseSunset.sunrise
    val sunsetTime = SunriseSunset.sunset

    val isDayTime = currentTime in sunriseTime!! until sunsetTime!!

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

@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String) {
    Picasso.get().load("https://openweathermap.org/img/wn/$url@2x.png").into(imageView)
}