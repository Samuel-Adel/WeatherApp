package com.example.weatherapp.google_map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.favourite_screen.viewModel.FavLocationViewModel
import com.example.weatherapp.favourite_screen.viewModel.FavLocationViewModelFactory
import com.example.weatherapp.model.DataSourceRepositoryImpl
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.example.weatherapp.util.AppPreferencesManagerValues
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class GoogleMapScreen : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var geocoder: Geocoder
    private var lat: Double = 30.0444
    private var lon: Double = 31.2357
    private var name: String = "Cairo"
    private lateinit var favLocationViewModel: FavLocationViewModel
    private lateinit var favLocationViewModelFactory: FavLocationViewModelFactory
    private var extraValue: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.google_map)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitLocation)
        extraValue = intent.getStringExtra("extra_key")

        viewModelSetup()
        btnSubmit.setOnClickListener {
            Log.i("address", "on Locatoin choosen: " + name + " " + lat + " " + lon)
            if (extraValue == null) {
                favLocationViewModel.addFavLocation(
                    FavouriteLocation(
                        lat = lat,
                        lon = lon,
                        name = name
                    )
                )
            } else {
                updateLonLat()
            }
            onBackPressed()

        }
        mapSetup()
    }

    private fun mapSetup() {
        geocoder = Geocoder(this)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val mapOptionsButton: ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this, mapOptionsButton)
        popupMenu.menuInflater.inflate(R.menu.map_view_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }
        mapOptionsButton.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun changeMap(itemId: Int) {
        when (itemId) {
            R.id.normal_map -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            R.id.hybrid_map -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            }

            R.id.satellite_map -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            R.id.terrain_map -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val cairo = LatLng(30.0444, 31.2357)
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 10f))
        addMarker(LatLng(30.0444, 31.2357))
        mGoogleMap?.setOnMapClickListener { latLng ->
            Log.i("Google MAps", "Clicked: ")
            lat = latLng.latitude
            lon = latLng.longitude
            addMarker(latLng)

        }
    }

    private fun addMarker(latLng: LatLng) {
        mGoogleMap?.clear()
        getAddressFromCoordinates(latLng.latitude, latLng.longitude) {

            mGoogleMap?.addMarker(
                MarkerOptions().position(latLng).title(it)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_marker))
                    .draggable(true)
            )
        }
    }

    private fun getAddressFromCoordinates(
        latitude: Double, longitude: Double, callback: (String) -> Unit
    ) {
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                name = address.adminArea.split(" ").first()
                val addressText = address.getAddressLine(0)
                callback(addressText)
            } else {
                Log.i("Google MAps", "No address found for the provided location")
            }
        } catch (e: IOException) {
            Log.i("Google MAps", "Error fetching address: ${e.message}")
        }

    }

    private fun viewModelSetup() {
        favLocationViewModelFactory = FavLocationViewModelFactory(
            DataSourceRepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(this),
                RemoteDataSourceImpl.getInstance()
            )
        )
        favLocationViewModel =
            ViewModelProvider(this, favLocationViewModelFactory)[FavLocationViewModel::class.java]
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun updateLonLat() {
        if (extraValue != null) {
            AppPreferencesManagerValues.saveLonLat(lon, lat)
            Log.i("LatLon", "updateLonLat: " + lat + " " + lon)
        }
    }
}