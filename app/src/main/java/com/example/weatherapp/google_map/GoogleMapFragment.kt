package com.example.weatherapp.google_map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import com.example.weatherapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class GoogleMapFragment : Fragment(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var geocoder: Geocoder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapSetup(view)
    }

    private fun mapSetup(view: View) {
        geocoder = Geocoder(requireContext())
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val mapOptionsButton: ImageButton = view.findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(requireContext(), mapOptionsButton)
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
                val addressText = address.getAddressLine(0)
                callback(addressText)
            } else {
                Log.i("Google MAps", "No address found for the provided location")
            }
        } catch (e: IOException) {
            Log.i("Google MAps", "Error fetching address: ${e.message}")
        }

    }

}