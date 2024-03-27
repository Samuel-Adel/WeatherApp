package com.example.weatherapp.favourite_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavouriteScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabAddFavLocation = view.findViewById<FloatingActionButton>(R.id.fabAddFavLocation)
        fabAddFavLocation.setOnClickListener {
            findNavController().navigate(R.id.googleMapScreen)
        }
    }
}