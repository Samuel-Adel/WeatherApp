package com.example.weatherapp.util

sealed class DataSourceState {
    class Success<T>(val data: T) : DataSourceState()
    class Failure(val msg: Throwable) : DataSourceState()
    data object Loading : DataSourceState()
}