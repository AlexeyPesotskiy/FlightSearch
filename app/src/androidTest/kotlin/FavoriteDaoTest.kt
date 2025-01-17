import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearch.data.FlightsDatabase
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var flightsDatabase: FlightsDatabase
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        flightsDatabase = Room.inMemoryDatabaseBuilder(context, FlightsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = flightsDatabase.favoriteDao()
    }

    private val favorite0 = Favorite(0, "a0", "b0")
    private val favorite1 = Favorite(1, "a1", "b1")

    private suspend fun addOneFavoriteToDb() {
        favoriteDao.insert(favorite0)
    }

    private suspend fun addTwoFavoritesToDb() {
        favoriteDao.insert(favorite0)
        favoriteDao.insert(favorite1)
    }

    @Test
    fun daoInsert_insertsFlightIntoDb() = runBlocking {
        addOneFavoriteToDb()
        val allItems = favoriteDao.selectAllFavoriteFlights().first()
        assertEquals(allItems[0], favorite0)
    }

    @Test
    fun daoDelete_deleteFlightFromDb() = runBlocking {
        addTwoFavoritesToDb()
        favoriteDao.delete(favorite0.departureCode, favorite0.destinationCode)
        val allItems = favoriteDao.selectAllFavoriteFlights().first()
        assertEquals(allItems.size, 1)
    }

    @Test
    fun daoGetAllFavorite_returnAllFlightsFromDb() = runBlocking {
        addTwoFavoritesToDb()
        val allItems = favoriteDao.selectAllFavoriteFlights().first()
        assertEquals(allItems[0], favorite0)
        assertEquals(allItems[1], favorite1)
    }

    @Test
    fun daoGetAllFavoriteFrom_returnAllFlightsFromAirportFromDb() = runBlocking {
        addTwoFavoritesToDb()
        val allItems = favoriteDao.selectFavoriteDestinationsFromAirport(favorite0.departureCode).first()
        assertEquals(allItems[0], favorite0.departureCode)
    }

    @After
    fun closeDb() {
        flightsDatabase.close()
    }
}