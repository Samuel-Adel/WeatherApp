package com.example.weatherapp.home_screen.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.home_screen.viewModel.HomeScreenViewModel
import com.example.weatherapp.home_screen.viewModel.HomeScreenViewModelFactory
import com.example.weatherapp.model.DataSourceRepositoryImpl

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.example.weatherapp.util.DataSourceState
import com.example.weatherapp.util.SunriseSunset
import com.example.weatherapp.util.TemperatureUnit
import com.example.weatherapp.util.addDegreeSymbol
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreen : Fragment() {
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var homeScreenHourlyWeatherAdapter: HourlyWeatherAdapter
    private lateinit var hourlyWeatherRV: RecyclerView
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var progressBar: LottieAnimationView
    private lateinit var locationName: TextView
    private lateinit var temperature: TextView
    private lateinit var weatherStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresher = view.findViewById(R.id.swipeAndRefresh)
        hourlyWeatherRV = view.findViewById(R.id.rvHourlyWeather)
        progressBar = view.findViewById(R.id.lottieAnimationLoading)
        locationName = view.findViewById(R.id.txtVLocationName)
        temperature = view.findViewById(R.id.txtVTemperature)
        weatherStatus = view.findViewById(R.id.txtVWeatherStatus)
        refresher.setColorSchemeResources(R.color.gigas)

        recyclerViewSetup()
        viewModelSetup()
        refresher.setOnRefreshListener {
            homeScreenViewModel.getWeatherData()
        }

    }

    private fun updateTxtView(weatherData: WeatherData) {
        locationName.text = weatherData.timezone.split("/").last()
        temperature.text = weatherData.current.temperature.toString().addDegreeSymbol()
        val description = weatherData.current.weather.first().description
        val capitalizedDescription = description.split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
        weatherStatus.text = capitalizedDescription
    }

    private fun recyclerViewSetup() {
        homeScreenHourlyWeatherAdapter = HourlyWeatherAdapter()
        hourlyWeatherRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourlyWeatherRV.adapter = homeScreenHourlyWeatherAdapter
    }

    private fun viewModelSetup() {
        val homeScreenViewModelFactory = HomeScreenViewModelFactory(
            DataSourceRepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(requireContext()),
                RemoteDataSourceImpl.getInstance()
            )
        )
        homeScreenViewModel =
            ViewModelProvider(this, homeScreenViewModelFactory)[HomeScreenViewModel::class.java]
        homeScreenViewModel.getWeatherData()
        lifecycleScope.launch {
            homeScreenViewModel.weatherData.collectLatest { result ->
                if (result is DataSourceState.Loading) {
                    refresher.isRefreshing = false
                    progressBar.visibility = View.VISIBLE
                } else {
                    refresher.isRefreshing = false
                    progressBar.visibility = View.GONE
                }
                if (result is DataSourceState.Success<*>) {
                    if (result.data is WeatherData) {
                        updateTxtView(result.data)
                        SunriseSunset.sunrise=result.data.current.sunrise
                        SunriseSunset.sunset=result.data.current.sunset
                        homeScreenHourlyWeatherAdapter.submitList(
                            result.data.hourly
                        )
                    } else {
                        Toast.makeText(
                            requireContext(), R.string.wrong_data_submitted, Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (result is DataSourceState.Failure) {
                    progressBar.visibility = View.GONE
                    Log.i("WeatherResponse", result.msg.message.toString())
                    Toast.makeText(
                        requireContext(), result.msg.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }
}