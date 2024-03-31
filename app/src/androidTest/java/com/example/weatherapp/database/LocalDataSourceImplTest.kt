import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.weatherapp.database.LocalDataSourceImpl
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.model.AlarmItem
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.FavouriteLocation
import com.example.weatherapp.model.WeatherCondition
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataSourceState
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@SmallTest
class LocalDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WeatherDatabase
    private lateinit var localDataSource: LocalDataSourceImpl
    val weatherData = WeatherData(
        40.7128,
        -74.0060,
        "America/New_York",
        -14400,
        CurrentWeather(
            System.currentTimeMillis() / 1000,
            System.currentTimeMillis() / 1000,
            System.currentTimeMillis() / 1000,
            20.5,
            19.0,
            1010,
            60,
            10.0,
            5.0,
            20,
            10000,
            5.0,
            180,
            7.0,
            listOf(
                WeatherCondition(
                    800,
                    "Clear",
                    "clear sky",
                    "01d"
                )
            )
        ),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList()
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java).build()
        localDataSource = LocalDataSourceImpl.getInstance(context)
    }

    @After
    fun shutdown() {
        database.close()
    }

    @Test
    fun getFavLocationsList_retrievesFavLocations() = runTest {
        // Given
        val favLocation1 = FavouriteLocation(1, 40.0, -73.0, "New York")
        val favLocation2 = FavouriteLocation(2, 34.0, -118.0, "Los Angeles")
        val favLocations = listOf(favLocation1, favLocation2)
        favLocations.forEach { localDataSource.addFavLocation(it) }

        // When
        val retrievedFavLocations = localDataSource.getFavLocationsList().first()

        // Then
        assertEquals(favLocations, retrievedFavLocations)
    }

    @Test
    fun saveWeatherDataAndRetrieveTheResult() = runTest {
        // Given
        val weatherData = weatherData

        // When
        localDataSource.saveWeatherData(weatherData)

        // Then
        val savedData = database.getWeatherDao().getWeatherList().firstOrNull()
        assertNotNull(savedData)
        val parsedWeatherData = Gson().fromJson(savedData?.json, WeatherData::class.java)
        assertNotNull(parsedWeatherData)
        assertEquals(weatherData, parsedWeatherData)
    }


    @Test
    fun getSavedWeatherList_emitsLoadingThenSuccess() = runTest {
        // Given
        localDataSource.saveWeatherData(weatherData)

        // When
        val flow = localDataSource.getSavedWeatherList()

        // Then
        val state = flow.first()
        assertEquals(DataSourceState.Loading, state)

        val nextState = flow.first()
        assertEquals(DataSourceState.Success(weatherData), nextState)
    }


    @Test
    fun addFavLocation_addsFavLocationCorrectly() = runTest {
        // Given
        val favLocation = FavouriteLocation(1, 40.0, -73.0, "New York")

        // When
        localDataSource.addFavLocation(favLocation)

        // Then
        val retrievedFavLocations = localDataSource.getFavLocationsList().first()
        assertEquals(listOf(favLocation), retrievedFavLocations)
    }

    @Test
    fun deleteFavLocation_deletesFavLocationCorrectly() = runTest {
        // Given
        val favLocation = FavouriteLocation(1, 40.0, -73.0, "New York")
        localDataSource.addFavLocation(favLocation)

        // When
        localDataSource.deleteFavLocation(favLocation)

        // Then
        val retrievedFavLocations = localDataSource.getFavLocationsList().first()
        assertEquals(emptyList<FavouriteLocation>(), retrievedFavLocations)
    }

    @Test
    fun geAlarmsList_retrievesAlarmsList() = runTest {
        // Given
        val alarm1 = AlarmItem( LocalDateTime.now(), 40.0, -73.0)
        val alarm2 = AlarmItem( LocalDateTime.now().plusDays(1), 34.0, -118.0)
        val alarms = listOf(alarm1, alarm2)
        alarms.forEach { localDataSource.addAlarm(it) }

        // When
        val retrievedAlarms = localDataSource.geAlarmsList().first()

        // Then
        assertEquals(alarms, retrievedAlarms)
    }

    @Test
    fun addAlarm_addsAlarmCorrectly() = runTest {
        // Given
        val alarm = AlarmItem( LocalDateTime.now(), 40.0, -73.0)

        // When
        localDataSource.addAlarm(alarm)

        // Then
        val retrievedAlarms = localDataSource.geAlarmsList().first()
        assertEquals(listOf(alarm), retrievedAlarms)
    }

    @Test
    fun deleteAlarm_deletesAlarmCorrectly() = runTest {
        // Given
        val alarm = AlarmItem( LocalDateTime.now(), 40.0, -73.0)
        localDataSource.addAlarm(alarm)

        // When
        localDataSource.deleteAlarm(alarm)

        // Then
        val retrievedAlarms = localDataSource.geAlarmsList().first()
        assertEquals(emptyList<AlarmItem>(), retrievedAlarms)
    }

}
