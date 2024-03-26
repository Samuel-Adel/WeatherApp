package com.example.weatherapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.util.AppPreferencesManagerValues
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavBar: CurvedBottomNavigation
    override fun onCreate(savedInstanceState: Bundle?) {
        configureAppLanguage()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavBar = findViewById(R.id.bottomNavigation)
        bottomNavBar.setupNavController(navController)
        setupNavBar()


    }

    private fun configureAppLanguage() {
        AppPreferencesManagerValues.prefsSetup(context = baseContext)
        when (AppPreferencesManagerValues.language) {
            null -> {
                changeLanguage("en")
            }

            getString(R.string.en) -> {
                changeLanguage("en")
            }

            else -> {
                changeLanguage("ar")
            }
        }
    }


    private fun setupNavBar() {
        bottomNavBar.add(
            CurvedBottomNavigation.Model(
                R.id.homeScreen, getString(R.string.home), R.drawable.home_icon
            )
        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(
                R.id.favouriteScreen, getString(R.string.favourite), R.drawable.fav_icon
            )

        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(
                R.id.alarmScreen, getString(R.string.alarm), R.drawable.alarm_icon
            )

        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(
                R.id.settingsFragment, getString(R.string.settings), R.drawable.setting_icon
            )
        )
        bottomNavBar.setOnClickMenuListener {
            navController.navigate(it.id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }

}