package com.example.weatherapp.home_screen.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
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
import com.example.weatherapp.util.AppPreferencesManagerValues
import com.example.weatherapp.util.DataSourceState
import com.example.weatherapp.util.GPSHandler
import com.example.weatherapp.util.REQUEST_LOCATION_CODE
import com.example.weatherapp.util.Temperature
import com.example.weatherapp.util.WeatherHandlingHelper
import com.example.weatherapp.util.addDegreeSymbol
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class HomeScreen : Fragment() {
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
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lat: Double? = null
    private var lon: Double? = null
    private var loaded: Boolean = false
    private lateinit var weatherAttributesCard: CardView
    private lateinit var pressureTxtV: TextView
    private lateinit var humidityTxtV: TextView
    private lateinit var cloudsTxtV: TextView
    private lateinit var windSpeedTxtV: TextView
    private lateinit var visibilityTxtV: TextView
    private lateinit var ultraVioletTxtV: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiSetup(view)
        recyclerViewsSetup()
        viewModelSetup()
        // showLocationDialog()
        onGPsChosen()
        //  mapSetup()
    }

    private fun mapSetup() {
//        geocoder = Geocoder(requireContext())
//        mapSupportFragment =
//            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//        mapSupportFragment.getMapAsync(this)

    }

    private fun showLocationDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.gps_map_dialog, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.setIcon(R.drawable.light_foggy)
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        val rgGpsOrMap = dialogView.findViewById<RadioGroup>(R.id.rgGpsOrMap)
        dialogView.findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            when (rgGpsOrMap.checkedRadioButtonId) {
                R.id.radioOpenMap -> {
                }

                else -> {
                    onGPsChosen()
                    dialog.cancel()
                }
            }

        }
    }

    private fun uiSetup(view: View) {
        pressureTxtV = view.findViewById(R.id.txtVPressureValue)
        humidityTxtV = view.findViewById(R.id.txtVHumidityValue)
        cloudsTxtV = view.findViewById(R.id.txtVCloudsValue)
        windSpeedTxtV = view.findViewById(R.id.txtVWindSpeedValue)
        visibilityTxtV = view.findViewById(R.id.txtVVisibilityValue)
        ultraVioletTxtV = view.findViewById(R.id.txtVUltraVioletValue)
        weatherAttributesCard = view.findViewById(R.id.cardViewWeatherAttributes)
        hourlyWeatherRV = view.findViewById(R.id.rvHourlyWeather)
        daysWeatherRV = view.findViewById(R.id.rvDaysWeather)
        progressBar = view.findViewById(R.id.lottieAnimationLoading)
        locationName = view.findViewById(R.id.txtVLocationName)
        temperature = view.findViewById(R.id.txtVTemperature)
        weatherStatus = view.findViewById(R.id.txtVWeatherStatus)
        weatherStatusImg = view.findViewById(R.id.imgWeatherStatus)
        refresher = view.findViewById(R.id.swipeAndRefresh)
        refresher.setColorSchemeResources(R.color.gigas)
        refresher.setOnRefreshListener {
            getFreshLocation()
        }
        progressBar.visibility = View.VISIBLE
    }

    private fun updateTxtView(weatherData: WeatherData) {
        homeScreenViewModel.saveWeatherDataLocally(weatherData)
        weatherAttributesCard.visibility = View.VISIBLE
        pressureTxtV.text = weatherData.current.pressure.toString()
        humidityTxtV.text = weatherData.current.humidity.toString()
        cloudsTxtV.text = weatherData.current.clouds.toString()
        windSpeedTxtV.text = weatherData.current.windSpeed.toString()
        visibilityTxtV.text = weatherData.current.visibility.toString()
        ultraVioletTxtV.text = weatherData.current.uvIndex.toString()
        locationName.text = weatherData.timezone.split("/").last()
        temperature.text = Temperature.convertTo(
            value = weatherData.current.temperature,
            context = requireContext(),
            targetUnitKey = AppPreferencesManagerValues.tempUnit
        ).toInt().toString().addDegreeSymbol()
        weatherStatusImg.setImageResource(WeatherHandlingHelper.getWeatherImage(weatherData.current))
        val description = weatherData.current.weather.first().description
        val capitalizedDescription = description.split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
        weatherStatus.text = capitalizedDescription
    }

    private fun recyclerViewsSetup() {
        homeScreenHourlyWeatherAdapter = HourlyWeatherAdapter(requireContext())
        homeScreenDailyWeatherAdapter = DaysWeatherAdapter()
        hourlyWeatherRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        daysWeatherRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        daysWeatherRV.adapter = homeScreenDailyWeatherAdapter
        daysWeatherRV.isNestedScrollingEnabled = false
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
    }

    private fun fetchingDataSetup(lat: Double, lon: Double) {
        if (!isDetached && view != null) {
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
                                requireContext(), R.string.wrong_data_submitted, Toast.LENGTH_SHORT
                            ).show()
                            refresher.isRefreshing = false
                            progressBar.visibility = View.GONE
                        }
                    } else if (result is DataSourceState.Failure) {
                        progressBar.visibility = View.GONE
                        Log.i("WeatherResponse", result.msg.message.toString())
                        Toast.makeText(
                            requireContext(),
                            R.string.this_location_does_not_contain_data,
                            Toast.LENGTH_SHORT
                        ).show()
                        refresher.isRefreshing = false
                        progressBar.visibility = View.GONE
                        homeScreenViewModel.getLocalWeatherData()
                    }

                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i("Permissions", "onRequestPermissionsResult: ${grantResults[0]}")
//        Log.i("Permissions", "onRequestPermissionsResult: $requestCode")
        if (requestCode == REQUEST_LOCATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkAndEnableLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getFreshLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.requestLocationUpdates(LocationRequest.Builder(0).apply {
            setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        }.build(), object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                if (location != null) {
                    Log.i("FreshLocation", "onLocationResult: ")
                    fetchingDataSetup(location.longitude, location.latitude)
                    lon = location.longitude
                    lat = location.latitude
                    loaded = true
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }, Looper.myLooper()
        )
    }

    private fun onGPsChosen() {
        if (GPSHandler.checkPermission(requireContext())) {
            checkAndEnableLocation()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_LOCATION_CODE
            )
        }
    }

    private fun checkAndEnableLocation() {
        if (GPSHandler.locationIsEnabled(requireActivity())) {
            getFreshLocation()
        } else {
            GPSHandler.enableLocationServices(requireContext())
            getFreshLocation()
        }
    }


}