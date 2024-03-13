package com.example.weatherapp.network
import com.example.weatherapp.model.UpperClassPojo
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getWeather(): UpperClassPojo
}