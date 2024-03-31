package com.example.weatherapp.alarm_screen.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.IAlarmSchedulerRepository
import com.example.weatherapp.model.IDataSourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class AlarmScreenViewModelTest {

    private lateinit var viewModel: AlarmScreenViewModel
    private lateinit var dataSourceRepository: IDataSourceRepository
    private lateinit var alarmSchedulerRepository: IAlarmSchedulerRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule

    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dataSourceRepository = FakeDataSourceRepository()
        alarmSchedulerRepository = FakeAlarmSchedulerRepository()
        viewModel = AlarmScreenViewModel(dataSourceRepository, alarmSchedulerRepository)
    }

    @Test
    fun deleteAlarmAlert() = runTest {
        val time = LocalDateTime.now()
        val latitude = 40.7128
        val longitude = -74.0060
        val alarmItem = AlarmItem(time = time, latitude = latitude, longitude = longitude)
        val currentAlarmItem = alarmItem

        viewModel.scheduleAlarm(currentAlarmItem)
        viewModel.cancelAlarm(currentAlarmItem)
        viewModel.getAlarms()
        val alarmsList = viewModel.alarmsList.first()
        assertEquals(emptyList<AlarmItem>(), alarmsList)
    }

    @Test
    fun insertAlarmAlert() = runTest {
        val time = LocalDateTime.now()
        val latitude = 40.7128
        val longitude = -74.0060
        val alarmItem = AlarmItem(time = time, latitude = latitude, longitude = longitude)
        val currentAlarmItem = alarmItem
        viewModel.scheduleAlarm(currentAlarmItem)
        viewModel.getAlarms()
        val alarmsList = viewModel.alarmsList.first()
        assertEquals(alarmItem, alarmsList.first())
    }


}
