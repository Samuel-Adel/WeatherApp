package com.example.weatherapp.favourite_screen.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.google_map.GoogleMapScreen
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
    private lateinit var favouriteLocationItemAdapter: FavLocationItemAdapter
    private lateinit var favLocationsRV: RecyclerView

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
        uiSetup(view)
        favLocationViewModel.favLocations
        fabAddFavLocation.setOnClickListener {
            val intent = Intent(requireContext(), GoogleMapScreen::class.java)
            startActivity(intent)
        }
        lifecycleScope.launch {
            favLocationViewModel.favLocations.collect { favLocations ->
                favouriteLocationItemAdapter.submitList(favLocations)
            }
        }
    }

    private fun uiSetup(view: View) {
        favLocationsRV = view.findViewById(R.id.rvFavLocations)
        favLocationsRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favouriteLocationItemAdapter = FavLocationItemAdapter {
            favLocationViewModel.deleteFromFavorites(it)
        }
        favLocationsRV.adapter = favouriteLocationItemAdapter

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