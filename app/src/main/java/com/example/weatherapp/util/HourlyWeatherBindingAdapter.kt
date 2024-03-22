package com.example.weatherapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.example.weatherapp.model.HourlyWeather
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
fun getWeatherImage(img: ImageView, weatherModel:HourlyWeather) {
    val imgSelected = when (weatherModel.weather.first().main) {
        "Rain" -> {
            when {
                weatherModel.weather.first().description.contains("heavy", ignoreCase = true) -> R.drawable.shower
                else -> R.drawable.sun_mid_rain
            }
        }

        "Clouds" -> R.drawable.fast_wind
        "Clear" -> R.drawable.sun_mid_rain
        else -> R.drawable.sun_mid_rain
    }
    img.setImageResource(imgSelected)
}