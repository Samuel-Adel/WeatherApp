package com.example.weatherapp.settings_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.weatherapp.R
import com.example.weatherapp.google_map.GoogleMapScreen

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(android.R.color.white))

        val languagePreference: ListPreference? = findPreference(getString(R.string.language_key))
        val locationPreference: ListPreference? = findPreference(getString(R.string.location_key))
        val tempUnitPreference: ListPreference? = findPreference(getString(R.string.temp_unit_key))
        val speedUnitPreference: ListPreference? =
            findPreference(getString(R.string.speed_unit_key))
        val notificationPreference: SwitchPreferenceCompat? =
            findPreference(getString(R.string.notification_key))

        languagePreference?.setOnPreferenceChangeListener { _, newValue ->
            Log.i("Pref", "onViewCreated: " + " " + newValue)
            if (newValue is String) {
                applyChanges()
            } else {
                false
            }
        }

        locationPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.i("Pref", "onViewCreated: " + preference + " " + newValue)
            if (newValue == resources.getString(R.string.map_key)) {
                val intent = Intent(requireContext(), GoogleMapScreen::class.java)
                intent.putExtra("extra_key", "settings")
                startActivity(intent)
            }
            true

        }

        tempUnitPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.i("Pref", "onViewCreated: " + preference + " " + newValue)
            applyChanges()
        }

        speedUnitPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.i("Pref", "onViewCreated: " + preference + " " + newValue)
            applyChanges()
        }

        notificationPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Log.i("Pref", "onViewCreated: " + preference + " " + newValue)

            true // Return true to update the preference value
        }
    }

    private fun applyChanges(): Boolean {
        requireActivity().recreate()
        return true
    }
}