package com.example.weatherapp.favourite_screen.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.alarm_screen.viewModel.FakeDataSourceRepository
import com.example.weatherapp.alarm_screen.viewModel.MainDispatcherRule
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.IDataSourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavLocationViewModelTest {

    private lateinit var viewModel: FavLocationViewModel
    private lateinit var dataSourceRepository: IDataSourceRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule

    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dataSourceRepository = FakeDataSourceRepository()
        viewModel = FavLocationViewModel(dataSourceRepository)
    }

    @Test
    fun deleteAlarmAlert() = runTest {
        val lat = 40.7128
        val lon = -74.0060
        val name = "Fayoum"
        val favLocation = FavouriteLocation(lat = lat, lon = lon, name = name)
        viewModel.addFavLocation(favLocation)
        viewModel.deleteFromFavorites(favLocation)
        val favList = viewModel.favLocations.first()
        Assert.assertEquals(emptyList<AlarmItem>(), favList)
    }

    @Test
    fun insertAlarmAlert() = runTest {
        val lat = 40.7128
        val lon = -74.0060
        val name = "Fayoum"
        val favLocation = FavouriteLocation(lat = lat, lon = lon, name = name)
        viewModel.addFavLocation(favLocation)
        val alarmsList = viewModel.favLocations.first()
        Assert.assertEquals(favLocation, alarmsList.first())
    }


}
