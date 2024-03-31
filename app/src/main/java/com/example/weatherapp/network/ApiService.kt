package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&units=metric&appid=e460f5006298cd345174a78ae174f4cf
interface ApiService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String = "metric",
        @Query("appid") appid: String = "e460f5006298cd345174a78ae174f4cf",
        ): Response<WeatherData>
}