package com.example.weatherapp.favourite_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.IDataSourceRepository

class FavLocationViewModelFactory(private val repo: IDataSourceRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavLocationViewModel::class.java))
            FavLocationViewModel(repo) as T
        else
            throw IllegalArgumentException("This view Model is Unknown!!<Fav View Model Supposed>")
    }

}