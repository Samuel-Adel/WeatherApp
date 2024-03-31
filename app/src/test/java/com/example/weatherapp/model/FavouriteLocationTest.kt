package com.example.weatherapp.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FavouriteLocationTest {

    @Test
    fun testConstruction() {
        val lat = 40.7128
        val lon = -74.0060
        val name = "New York"

        val favouriteLocation = FavouriteLocation(lat = lat, lon = lon, name = name)

        assertThat(lat, `is`(favouriteLocation.lat))
        assertThat(lon, `is`(favouriteLocation.lon))
        assertThat(name, `is`(favouriteLocation.name))
    }

    @Test
    fun testEquality() {
        val lat = 40.7128
        val lon = -74.0060
        val name = "Fayoum"
        val favouriteLocation1 = FavouriteLocation(lat = lat, lon = lon, name = name)
        val favouriteLocation2 = FavouriteLocation(lat = lat, lon = lon, name = name)
        assertThat(favouriteLocation1, `is`(favouriteLocation2))
    }
}