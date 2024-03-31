package com.example.weatherapp.alarm_screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.IAlarmSchedulerRepository
import com.example.weatherapp.model.IDataSourceRepository

class AlarmScreenViewModelFactory(
    private val repo: IDataSourceRepository,
    private val alarmRepository: IAlarmSchedulerRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmScreenViewModel::class.java))
            AlarmScreenViewModel(repo, alarmRepository) as T
        else
            throw IllegalArgumentException("This view Model is Unknown!!<Alarm View Model Supposed>")
    }

}