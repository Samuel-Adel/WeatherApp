package com.example.weatherapp.home_screen.view

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyWeatherItemBinding
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.util.TemperatureUnit

class HourlyWeatherAdapter :
    ListAdapter<HourlyWeather, HourlyWeatherAdapter.HourlyWeatherViewHolder>(HourlyWeatherDiffUtil()) {
    private lateinit var binding: HourlyWeatherItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.hourly_weather_item, parent, false)
        val cardView = binding.cvHourlyWeatherItem
        applyGradientBackground(cardView)

        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentWeather = getItem(position)
        Log.i("Apadter", "onBindViewHolder: " + currentWeather.clouds)
        Log.i("Apadter", "onBindViewHolder: " + currentWeather.temp)
        Log.i("Apadter", "onBindViewHolder: " + currentWeather.dt)
        holder.binding.weatherModel = currentWeather

    }

    inner class HourlyWeatherViewHolder(val binding: HourlyWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    class HourlyWeatherDiffUtil : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem == newItem
        }

    }

    private fun applyGradientBackground(cardView: CardView) {
        val gradientDrawable =
            cardView.resources.getDrawable(R.drawable.gradient_background) as GradientDrawable
        cardView.background = gradientDrawable
    }

}