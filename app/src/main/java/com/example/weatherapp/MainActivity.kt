package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavBar: CurvedBottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavBar.add(
            CurvedBottomNavigation.Model(1, getString(R.string.home), R.drawable.home_icon)

        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(2, getString(R.string.favourite), R.drawable.fav_icon)

        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(3, getString(R.string.alarm), R.drawable.alarm_icon)

        )
        bottomNavBar.add(
            CurvedBottomNavigation.Model(4, getString(R.string.settings), R.drawable.setting_icon)

        )
        bottomNavBar.show(1)
        bottomNavBar.setOnClickListener {
            when (it.id) {
                1 -> {
                    bottomNavBar.show(1)
                }

                2 -> {
                    bottomNavBar.show(2)
                }

                3 -> {
                    bottomNavBar.show(3)
                }

                4 -> {
                    bottomNavBar.show(4)
                }
            }
        }
    }
}