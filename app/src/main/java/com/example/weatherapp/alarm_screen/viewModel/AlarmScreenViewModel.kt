package com.example.weatherapp.alarm_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.IAlarmSchedulerRepository
import com.example.weatherapp.model.IDataSourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AlarmScreenViewModel(
    private val repo: IDataSourceRepository,
    private val alarmRepository: IAlarmSchedulerRepository
) : ViewModel() {

    private val _alarms = MutableStateFlow<List<AlarmItem>>(emptyList())
    val alarmsList: StateFlow<List<AlarmItem>>
        get() = _alarms

    init {
        getFavLocations()
    }

    private fun getFavLocations() =
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavLocationsList().collect {

            }
        }


    fun cancelAlarm(item: AlarmItem) {
        alarmRepository.cancel(item)
    }

    fun scheduleAlarm(item: AlarmItem) {
        alarmRepository.schedule(item)
    }


}