package com.example.weatherapp.model
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
class WeatherDataTest {

    @Test
    fun testConstruction() {
        val latitude = 40.7128
        val longitude = -74.0060
        val timezone = "America/New_York"
        val timezoneOffset = -14400
        val currentWeather = CurrentWeather(
            timestamp = 1617309600L,
            sunrise = 1617292796L,
            sunset = 1617343114L,
            temperature = 25.5,
            feelsLike = 26.7,
            pressure = 1014,
            humidity = 72,
            dewPoint = 17.2,
            uvIndex = 4.5,
            clouds = 40,
            visibility = 10000,
            windSpeed = 5.1,
            windDegree = 180,
            windGust = 6.2,
            weather = listOf(WeatherCondition(800, "Clear", "clear sky", "01d"))
        )
        val minutelyData = listOf(MinutelyData(1617309600L, 10))
        val hourlyWeather = listOf(
            HourlyWeather(
                timestamp = 1617309600L,
                temperature = 25.5,
                feelsLike = 26.7,
                pressure = 1014,
                humidity = 72,
                dewPoint = 17.2,
                uvIndex = 4.5,
                clouds = 40,
                visibility = 10000,
                windSpeed = 5.1,
                windDegree = 180,
                windGust = 6.2,
                weather = listOf(WeatherCondition(800, "Clear", "clear sky", "01d")),
                pop = 0.5
            )
        )
        val dailyData = listOf(
            DailyData(
                timestamp = 1617309600L,
                sunrise = 1617292796L,
                sunset = 1617343114L,
                moonrise = 1617312751L,
                moonSet = 1617352020L,
                moonPhase = 0.6,
                summary = "Clear",
                temperature = TemperatureData(15.0, 10.0, 20.0, 12.0, 18.0, 13.0),
                feelsLike = FeelsLikeData(16.0, 11.0, 19.0, 14.0),
                pressure = 1014,
                humidity = 72,
                dewPoint = 17.2,
                windSpeed = 5.1,
                windDegree = 180,
                windGust = 6.2,
                weather = listOf(WeatherCondition(800, "Clear", "clear sky", "01d")),
                clouds = 40,
                pop = 0.2,
                rain = 0.1,
                uvIndex = 4.5
            )
        )
        val alerts = listOf(
            Alert(
                senderName = "National Weather Service",
                event = "Heat Advisory",
                start = 1617350400L,
                end = 1617350400L,
                description = "Heat advisory remains in effect from noon today to 8 PM EDT this evening.",
                tags = listOf("Extreme heat")
            )
        )

        val weatherData = WeatherData(
            latitude = latitude,
            longitude = longitude,
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            current = currentWeather,
            minutely = minutelyData,
            hourly = hourlyWeather,
            daily = dailyData,
            alerts = alerts
        )

        assertThat(weatherData.latitude, `is`(latitude))
        assertThat(weatherData.longitude, `is`(longitude))
        assertThat(weatherData.timezone, `is`(timezone))
        assertThat(weatherData.timezoneOffset, `is`(timezoneOffset))
        assertThat(weatherData.current, `is`(currentWeather))
        assertThat(weatherData.minutely, `is`(minutelyData))
        assertThat(weatherData.hourly, `is`(hourlyWeather))
        assertThat(weatherData.daily, `is`(dailyData))
        assertThat(weatherData.alerts, `is`(alerts))
    }
}