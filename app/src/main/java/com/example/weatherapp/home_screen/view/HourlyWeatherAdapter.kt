package com.example.weatherapp.home_screen.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourlyWeatherItemBinding
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.util.AppPreferencesManagerValues
import com.example.weatherapp.util.Temperature

class HourlyWeatherAdapter(val context: Context) :
    ListAdapter<HourlyWeather, HourlyWeatherAdapter.HourlyWeatherViewHolder>(HourlyWeatherDiffUtil()) {
    private lateinit var binding: HourlyWeatherItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.hourly_weather_item, parent, false)
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentWeather = getItem(position)
        currentWeather.temperature = Temperature.convertTo(
            value = currentWeather.temperature,
            context = context,
            targetUnitKey = AppPreferencesManagerValues.tempUnit
        )
        Log.i("current Weather", "onBindViewHolder: " + currentWeather.temperature)
        holder.binding.weatherModel = currentWeather

    }

    inner class HourlyWeatherViewHolder(val binding: HourlyWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class HourlyWeatherDiffUtil : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem == newItem
        }

    }

}