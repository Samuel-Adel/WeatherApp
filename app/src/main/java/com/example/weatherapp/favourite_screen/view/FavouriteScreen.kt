package com.example.weatherapp.favourite_screen.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.favourite_screen.viewModel.FavLocationViewModel
import com.example.weatherapp.favourite_screen.viewModel.FavLocationViewModelFactory
import com.example.weatherapp.model.DataSourceRepositoryImpl
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class FavouriteScreen : Fragment() {
    private lateinit var favLocationViewModel: FavLocationViewModel
    private lateinit var favLocationViewModelFactory: FavLocationViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabAddFavLocation = view.findViewById<FloatingActionButton>(R.id.fabAddFavLocation)
        viewModelSetup()
        favLocationViewModel.favLocations
//        val latitude = arguments?.getDouble("latitude", 0.0) ?: 0.0
//        val longitude = arguments?.getDouble("longitude", 0.0) ?: 0.0
        fabAddFavLocation.setOnClickListener {
            findNavController().navigate(R.id.googleMapScreen)
        }
        lifecycleScope.launch {
            favLocationViewModel.favLocations.collect { favLocations ->
                Log.i("locations", "onViewCreated: " + favLocations.size)
            }
        }
    }

    private fun viewModelSetup() {
        favLocationViewModelFactory = FavLocationViewModelFactory(
            DataSourceRepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(requireContext()),
                RemoteDataSourceImpl.getInstance()
            )
        )
        favLocationViewModel =
            ViewModelProvider(this, favLocationViewModelFactory)[FavLocationViewModel::class.java]
    }
}