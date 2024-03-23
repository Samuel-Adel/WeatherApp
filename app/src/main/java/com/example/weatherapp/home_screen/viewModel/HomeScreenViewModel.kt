package com.example.weatherapp.home_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.IDataSourceRepository
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repo: IDataSourceRepository) : ViewModel() {
    private val _weatherData =
        MutableStateFlow<DataSourceState>(DataSourceState.Success(WeatherData::class.java))
    val weatherData: StateFlow<DataSourceState>
        get() = _weatherData



     fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWeatherList(0.0, 0.0)
                .catch { e ->
                    _weatherData.value = DataSourceState.Failure(e)
                }
                .collect {
                    _weatherData.value = it
                }

        }
    }
}