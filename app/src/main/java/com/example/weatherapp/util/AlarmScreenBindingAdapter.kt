package com.example.weatherapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@BindingAdapter("localDateToString")

fun localDateToString(textView: TextView, time: LocalDateTime) {
    val selectedDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant())
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDateTime = sdf.format(selectedDate).split(" ").first()
    textView.text = formattedDateTime
}

@BindingAdapter("localTimeToString")

fun localTimeToString(textView: TextView, time: LocalDateTime) {
    val selectedDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant())
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDateTime = sdf.format(selectedDate).split(" ").last()
    textView.text = formattedDateTime
}