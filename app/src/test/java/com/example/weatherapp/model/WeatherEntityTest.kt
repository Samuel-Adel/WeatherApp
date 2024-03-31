import com.example.weatherapp.model.WeatherEntity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class WeatherEntityTest {

    @Test
    fun testConstruction() {
        val id = 1
        val jsonData =
            "{\"lat\":40.7128,\"lon\":-74.0060,\"timezone\":\"America/New_York\",\"timezone_offset\":-14400,\"current\":{\"dt\":1617309600,\"sunrise\":1617292796,\"sunset\":1617343114,\"temp\":25.5,\"feels_like\":26.7,\"pressure\":1014,\"humidity\":72,\"dew_point\":17.2,\"uvi\":4.5,\"clouds\":40,\"visibility\":10000,\"wind_speed\":5.1,\"wind_deg\":180,\"wind_gust\":6.2,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}]},\"minutely\":[{\"dt\":1617309600,\"precipitation\":10}],\"hourly\":[{\"dt\":1617309600,\"temp\":25.5,\"feels_like\":26.7,\"pressure\":1014,\"humidity\":72,\"dew_point\":17.2,\"uvi\":4.5,\"clouds\":40,\"visibility\":10000,\"wind_speed\":5.1,\"wind_deg\":180,\"wind_gust\":6.2,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"pop\":0.5}],\"daily\":[{\"dt\":1617309600,\"sunrise\":1617292796,\"sunset\":1617343114,\"moonrise\":1617312751,\"moonset\":1617352020,\"moon_phase\":0.6,\"summary\":\"Clear\",\"temp\":{\"day\":15.0,\"min\":10.0,\"max\":20.0,\"night\":12.0,\"eve\":18.0,\"morn\":13.0},\"feels_like\":{\"day\":16.0,\"night\":11.0,\"eve\":19.0,\"morn\":14.0},\"pressure\":1014,\"humidity\":72,\"dew_point\":17.2,\"wind_speed\":5.1,\"wind_deg\":180,\"wind_gust\":6.2,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":40,\"pop\":0.2,\"rain\":0.1,\"uvi\":4.5}],\"alerts\":[{\"sender_name\":\"National Weather Service\",\"event\":\"Heat Advisory\",\"start\":1617350400,\"end\":1617350400,\"description\":\"Heat advisory remains in effect from noon today to 8 PM EDT this evening.\",\"tags\":[\"Extreme heat\"]}]}"
        val weatherEntity = WeatherEntity(id, jsonData)
        assertThat(weatherEntity.id, `is`(id))
        assertThat(weatherEntity.json, `is`(jsonData))
    }
}
