package com.example.weatherapp.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDateTime

class AlarmItemTest {

    @Test
    fun getTimeLatitudeLongitude_AlarmItem_CurrentTimeAndLonLatInsertedValues() {
        val time = LocalDateTime.now()
        val latitude = 40.7128
        val longitude = -74.0060
        val alarmItem = AlarmItem(time = time, latitude = latitude, longitude = longitude)
        assertThat(time, `is`(alarmItem.time))
        assertThat(latitude, `is`(alarmItem.latitude))
        assertThat(longitude, `is`(alarmItem.longitude))

    }

    @Test
    fun getEqualityResult_TwoAlertItems_EqualEachOther() {
        val time = LocalDateTime.now()
        val latitude = 40.7128
        val longitude = -74.0060
        val alarmItem1 = AlarmItem(time = time, latitude = latitude, longitude = longitude)
        val alarmItem2 = AlarmItem(time = time, latitude = latitude, longitude = longitude)
        assertThat(alarmItem1, `is`(alarmItem2))
    }
}