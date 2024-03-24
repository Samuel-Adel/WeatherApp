package com.example.weatherapp.home_screen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DayWeatherItemBinding
import com.example.weatherapp.model.DailyData

class DaysWeatherAdapter :
    ListAdapter<DailyData, DaysWeatherAdapter.DailyWeatherViewHolder>(DailyWeatherDiffUtil()) {
    private lateinit var binding: DayWeatherItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.day_weather_item, parent, false)
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val currentWeather = getItem(position)
        holder.binding.dayWeatherModel = currentWeather
    }

    inner class DailyWeatherViewHolder(val binding: DayWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DailyWeatherDiffUtil : DiffUtil.ItemCallback<DailyData>() {
        override fun areItemsTheSame(oldItem: DailyData, newItem: DailyData): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: DailyData, newItem: DailyData): Boolean {
            return oldItem == newItem
        }

    }

}