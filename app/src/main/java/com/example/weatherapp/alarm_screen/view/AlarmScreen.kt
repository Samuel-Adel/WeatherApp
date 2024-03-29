package com.example.weatherapp.alarm_screen.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.alarm_screen.viewModel.AlarmScreenViewModel
import com.example.weatherapp.alarm_screen.viewModel.AlarmScreenViewModelFactory
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.AlarmSchedulerRepositoryImpl
import com.example.weatherapp.model.DataSourceRepositoryImpl
import com.example.weatherapp.network.RemoteDataSourceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmScreen : Fragment(),
    DatePickerDialog.OnDateSetListener {
    private lateinit var rvAlarms: RecyclerView
    private lateinit var fabAddAlarm: FloatingActionButton
    private lateinit var alarmScreenViewModel: AlarmScreenViewModel
    private lateinit var alarmScreenViewModelFactory: AlarmScreenViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelSetup()
        rvAlarms = view.findViewById(R.id.rvAlarms)
        fabAddAlarm = view.findViewById(R.id.fabAddAlarm)
        fabAddAlarm.setOnClickListener {
            showDateTimePickerDialog()
        }
        onBackPressed()

    }

    private fun viewModelSetup() {
        alarmScreenViewModelFactory = AlarmScreenViewModelFactory(
            DataSourceRepositoryImpl.getInstance(
                LocalDataSourceImpl.getInstance(requireContext()),
                RemoteDataSourceImpl.getInstance()
            ), AlarmSchedulerRepositoryImpl(requireContext())
        )
        alarmScreenViewModel =
            ViewModelProvider(this, alarmScreenViewModelFactory)[AlarmScreenViewModel::class.java]
    }

    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), { _, hourOfDay, min ->
            val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, min)
            val selectedDate =
                Date.from(selectedDateTime.atZone(ZoneId.systemDefault()).toInstant())
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDateTime = sdf.format(selectedDate)
            alarmScreenViewModel.scheduleAlarm(
                AlarmItem(
                    time = selectedDateTime,
                    message = formattedDateTime
                )
            )
            Log.i("DateTime", "Selected DateTime: $formattedDateTime")
        }, hour, minute, true).show()
    }


    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeScreen)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}