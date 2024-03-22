package com.example.weatherapp.home_screen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherapp.R
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.Weather
import java.text.DecimalFormat
import kotlin.random.Random

class HomeScreen : Fragment() {
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var homeScreenHourlyWeatherAdapter: HourlyWeatherAdapter
    private lateinit var hourlyWeatherRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        refresher = view.findViewById(R.id.swipeAndRefresh)
        homeScreenHourlyWeatherAdapter = HourlyWeatherAdapter()
        hourlyWeatherRV = view.findViewById(R.id.rvHourlyWeather)
        hourlyWeatherRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        hourlyWeatherRV.adapter = homeScreenHourlyWeatherAdapter
        homeScreenHourlyWeatherAdapter.submitList(generateDummyHourlyWeatherList(24))
//        homeScreenHourlyWeatherAdapter = HourlyWeatherAdapter()
//        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_home_screen)
//        binding.lifecycleOwner = this
//        binding.hourlyWeatherAdapter = homeScreenHourlyWeatherAdapter
//        homeScreenHourlyWeatherAdapter.submitList(generateDummyHourlyWeatherList(24))
        refresher.setOnRefreshListener {

        }
    }

    private fun generateDummyHourlyWeatherList(size: Int): List<HourlyWeather> {
        val dummyList = mutableListOf<HourlyWeather>()

        // Create a DecimalFormat object to format temperature and feels_like values
        val decimalFormat = DecimalFormat("#.##")

        // Generate dummy data for each HourlyWeather object
        repeat(size) {
            val dummyWeather = Weather(
                id = Random.nextInt(200, 800),
                main = "Clear",
                description = "Clear sky",
                icon = "01d"
            )
            val dummyHourlyWeather = HourlyWeather(
                dt = System.currentTimeMillis() + it * 3600000,
                temp = decimalFormat.format(Random.nextDouble(20.0, 30.0)).toDouble(),
                feels_like = decimalFormat.format(Random.nextDouble(18.0, 28.0)).toDouble(),
                pressure = Random.nextInt(900, 1100),
                humidity = Random.nextInt(30, 70),
                dew_point = decimalFormat.format(Random.nextDouble(10.0, 20.0)).toDouble(),
                uvi = decimalFormat.format(Random.nextDouble(0.0, 10.0)).toDouble(),
                clouds = Random.nextInt(0, 100),
                visibility = Random.nextInt(1000, 10000),
                wind_speed = decimalFormat.format(Random.nextDouble(1.0, 10.0)).toDouble(),
                wind_deg = Random.nextInt(0, 360),
                wind_gust = decimalFormat.format(Random.nextDouble(1.0, 15.0)).toDouble(),
                weather = listOf(dummyWeather),
                pop = Random.nextInt(0, 100)
            )
            dummyList.add(dummyHourlyWeather)
        }

        return dummyList
    }
}