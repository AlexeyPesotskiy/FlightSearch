import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.FlightsDatabase
import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportDao
import com.example.flightsearch.data.favorite.Favorite
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AirportDaoTest {
    private lateinit var db: FlightsDatabase
    private lateinit var airportDao: AirportDao

    private val airport1 = Airport(
        1, "LED", "Pulkovo Airport", 1000000
    )
    private val airport2 = Airport(
        2, "VKO", "Vnukovo International Airport", 25260
    )

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FlightsDatabase::class.java
        ).allowMainThreadQueries().build()
        airportDao = db.airportDao()

        val writableDb = db.openHelper.writableDatabase

        writableDb.execSQL("INSERT INTO airport VALUES " +
                airport1.run { "($id, '$iataCode', '$name',$passengers)," } +
                airport2.run { "($id, '$iataCode', '$name', $passengers)" }
        )
    }

    @Test
    fun daoSelectAirportsListFromFavorites_returnAirportsFromFavorites() = runBlocking {
        db.favoriteDao().insert(
            Favorite(
                departureCode = airport1.iataCode,
                destinationCode = airport2.iataCode
            )
        )
        val airportsFromFavorites = airportDao.selectAirportsListFromFavorites().first()
        assertEquals(airportsFromFavorites, listOf(airport1, airport2))
    }

    @Test
    fun daoSelectAirportsMatchingQuery_returnMatchingQuery() = runBlocking {
        val searchQuery = "Pulkov"
        val allMatchingQuery = airportDao.selectAirportsMatchingQuery(searchQuery).first()
        assertEquals(allMatchingQuery[0], airport1)
    }

    @Test
    fun daoSelectAllAirportsExcludeCurrent_returnAllExcludeCurrent() = runBlocking {
        val allExcludeCurrent = airportDao
            .selectAllAirportsExcludeCurrent(airport1.iataCode).first()
        assertEquals(allExcludeCurrent[0], airport2)
    }

    @After
    fun closeDb() {
        db.close()
    }
}