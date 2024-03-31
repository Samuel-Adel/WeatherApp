package com.example.weatherapp.favourite_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.IDataSourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavLocationViewModel(private val repo: IDataSourceRepository) : ViewModel() {


    private val _favLocations = MutableStateFlow<List<FavouriteLocation>>(emptyList())
    val favLocations: StateFlow<List<FavouriteLocation>>
        get() = _favLocations

    init {
        getFavLocations()
    }

    private fun getFavLocations() =
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavLocationsList().collect {
                _favLocations.value = it
            }
        }


    fun deleteFromFavorites(location: FavouriteLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFavLocation(location)
            getFavLocations()
        }
    }

    fun addFavLocation(location: FavouriteLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addFavLocation(location)
            getFavLocations()
        }
    }

}