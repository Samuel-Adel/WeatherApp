package com.example.weatherapp.home_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.IDataSourceRepository

@Suppress("UNCHECKED_CAST")
class HomeScreenViewModelFactory(private val repo: IDataSourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java))
            HomeScreenViewModel(repo) as T
        else
            throw IllegalArgumentException("This view model is Unknown!!<Products View Model Supposed>")
    }
}