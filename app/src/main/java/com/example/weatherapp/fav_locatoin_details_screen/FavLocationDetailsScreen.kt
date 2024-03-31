package com.example.weatherapp.fav_locatoin_details_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.home_screen.view.DaysWeatherAdapter
import com.example.weatherapp.home_screen.view.HourlyWeatherAdapter
import com.example.weatherapp.home_screen.viewModel.HomeScreenViewModel
import com.example.weatherapp.home_screen.viewModel.HomeScreenViewModelFactory
import com.example.weatherapp.model.DataSourceRepositoryImpl
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.example.weatherapp.util.AppPreferencesManagerValues
import com.example.weatherapp.util.DataSourceState
import com.example.weatherapp.util.SpeedUnitConverter
import com.example.weatherapp.util.Temperature
import com.example.weatherapp.util.WeatherHandlingHelper
import com.example.weatherapp.util.addDegreeSymbol
import com.example.weatherapp.util.addSpeedUnit
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavLocationDetailsScreen : AppCompatActivity() {
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var homeScreenHourlyWeatherAdapter: HourlyWeatherAdapter
    private lateinit var homeScreenDailyWeatherAdapter: DaysWeatherAdapter
    private lateinit var hourlyWeatherRV: RecyclerView
    private lateinit var daysWeatherRV: RecyclerView
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var progressBar: LottieAnimationView
    private lateinit var locationName: TextView
    private lateinit var temperature: TextView
    private lateinit var weatherStatus: TextView
    private lateinit var weatherStatusImg: ImageView
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var name: String? = null
    private lateinit var weatherAttributesCard: CardView
    private lateinit var pressureTxtV: TextView
    private lateinit var humidityTxtV: TextView
    private lateinit var cloudsTxtV: TextView
    private lateinit var windSpeedTxtV: TextView
    private lateinit var visibilityTxtV: TextView
    private lateinit var ultraVioletTxtV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_location_details_screen)
        lat = intent.getDoubleExtra(resources.getString(R.string.lat), 0.0)
        lon = intent.getDoubleExtra(resources.getString(R.string.lon), 0.0)
        name = intent.getStringExtra(resources.getString(R.string.name))
        uiSetup()
        recyclerViewsSetup()
        viewModelSetup()
        fetchingDataSetup(lat, lon)

    }

    private fun uiSetup() {
        pressureTxtV = findViewById(R.id.txtVPressureValue)
        humidityTxtV = findViewById(R.id.txtVHumidityValue)
        cloudsTxtV = findViewById(R.id.txtVCloudsValue)
        windSpeedTxtV = findViewById(R.id.txtVWindSpeedValue)
        visibilityTxtV = findViewById(R.id.txtVVisibilityValue)
        ultraVioletTxtV = findViewById(R.id.txtVUltraVioletValue)
        weatherAttributesCard = findViewById(R.id.cardViewWeatherAttributes)
        hourlyWeatherRV = findViewById(R.id.rvHourlyWeather)
        daysWeatherRV = findViewById(R.id.rvDaysWeather)
        progressBar = findViewById(R.id.lottieAnimationLoading)
        locationName = findViewById(R.id.txtVLocationName)
        temperature = findViewById(R.id.txtVTemperature)
        weatherStatus = findViewById(R.id.txtVWeatherStatus)
        weatherStatusImg = findViewById(R.id.imgWeatherStatus)
        refresher = findViewById(R.id.swipeAndRefresh)
        refresher.setColorSchemeResources(R.color.gigas)
        refresher.setOnRefreshListener {
            fetchingDataSetup(lat, lon)
        }
        progressBar.visibility = View.VISIBLE
    }

    private fun recyclerViewsSetup() {
        homeScreenHourlyWeatherAdapter = HourlyWeatherAdapter(this)
        homeScreenDailyWeatherAdapter = DaysWeatherAdapter()
        hourlyWeatherRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        daysWeatherRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        daysWeatherRV.adapter = homeScreenDailyWeatherAdapter
        daysWeatherRV.isNestedScrollingEnabled = false
        hourlyWeatherRV.adapter = homeScreenHourlyWeatherAdapter
    }

    private fun updateTxtView(weatherData: WeatherData) {
        weatherAttributesCard.visibility = View.VISIBLE
        pressureTxtV.text = weatherData.current.pressure.toString()
        humidityTxtV.text = weatherData.current.humidity.toString()
        cloudsTxtV.text = weatherData.current.clouds.toString()
        windSpeedTxtV.text = SpeedUnitConverter.metersPerSecondToMilesPerHour(
            weatherData.current.windSpeed,
            SpeedUnitConverter.getUnitFromKey(
                this,
                AppPreferencesManagerValues.windSpeed
            )
        ).toString().addSpeedUnit(
            SpeedUnitConverter.getUnitFromKey(
                this,
                AppPreferencesManagerValues.windSpeed
            )
        )
        visibilityTxtV.text = weatherData.current.visibility.toString()
        ultraVioletTxtV.text = weatherData.current.uvIndex.toString()
        if (name == null) {
            locationName.text = weatherData.timezone.split("/").last()

        } else {
            locationName.text = name
        }
        temperature.text = Temperature.convertTo(
            value = weatherData.current.temperature,
            context = this,
            targetUnitKey = AppPreferencesManagerValues.tempUnit
        ).toInt().toString().addDegreeSymbol(
            Temperature.getUnitFromKey(
                this,
                AppPreferencesManagerValues.tempUnit
            )
        )
        weatherStatusImg.setImageResource(WeatherHandlingHelper.getWeatherImage(weatherData.current))
        val description = weatherData.current.weather.first().description
        val capitalizedDescription = description.split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
        weatherStatus.text = capitalizedDescription
    }

    private fun viewModelSetup() {

        val homeScreenViewModelFactory = HomeScreenViewModelFactory(
            DataSourceRepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(this),
                RemoteDataSourceImpl.getInstance()
            )
        )
        homeScreenViewModel =
            ViewModelProvider(this, homeScreenViewModelFactory)[HomeScreenViewModel::class.java]
    }

    private fun fetchingDataSetup(lat: Double, lon: Double) {

        homeScreenViewModel.getWeatherData(lat, lon)
        lifecycleScope.launch {
            homeScreenViewModel.weatherData.collectLatest { result ->
                if (result is DataSourceState.Loading && !refresher.isRefreshing) {
                    progressBar.visibility = View.VISIBLE
                } else if (result is DataSourceState.Success<*>) {
                    if (result.data is WeatherData) {
                        WeatherHandlingHelper.sunrise = result.data.current.sunrise
                        WeatherHandlingHelper.sunset = result.data.current.sunset
                        updateTxtView(result.data)
                        val modifiedList = result.data.daily.drop(1)
                        homeScreenDailyWeatherAdapter.submitList(modifiedList)
                        homeScreenHourlyWeatherAdapter.submitList(
                            result.data.hourly
                        )
                        refresher.isRefreshing = false
                        progressBar.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            this@FavLocationDetailsScreen,
                            R.string.wrong_data_submitted,
                            Toast.LENGTH_SHORT
                        ).show()
                        refresher.isRefreshing = false
                        progressBar.visibility = View.GONE
                    }
                } else if (result is DataSourceState.Failure) {
                    progressBar.visibility = View.GONE
                    Log.i("WeatherResponse", result.msg.message.toString())
                    Toast.makeText(
                        this@FavLocationDetailsScreen,
                        R.string.this_location_does_not_contain_data,
                        Toast.LENGTH_SHORT
                    ).show()
                    refresher.isRefreshing = false
                    progressBar.visibility = View.GONE
                }


            }
        }
    }

}