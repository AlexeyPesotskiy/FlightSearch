package com.example.flightsearch

import com.example.flightsearch.data.airport.AirportRepository
import org.junit.Test
import com.example.flightsearch.data.airport.OfflineAirportRepository
import com.example.flightsearch.data.favorite.FavoriteRepository
import com.example.flightsearch.data.favorite.OfflineFavoriteRepository
import com.example.flightsearch.fake.FakeAirportDao
import com.example.flightsearch.fake.FakeDataSource
import com.example.flightsearch.fake.FakeFavoriteDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before

/**
 * Тесты для [OfflineAirportRepository]
 */
class OfflineAirportRepositoryTest {

    private lateinit var repository: AirportRepository

    @Before
    fun createOfflineRepository() {
        repository = OfflineAirportRepository(FakeAirportDao())
    }

    @Test
    fun offlineAirportRepository_getAirportsMatchingQuery() = runTest {
        val airports = repository.getAirportsMatchingQuery("")
            .first()
        assertEquals(airports, FakeDataSource.airportsListResult)
    }

    @Test
    fun offlineAirportRepository_getAllDestinationsFromAirport() = runTest {
        val airports = repository.getAllDestinationsFromAirport("")
            .first()
        assertEquals(airports, FakeDataSource.airportsListResult)
    }

    @Test
    fun offlineAirportRepository_getAirportsListFromFavorites() = runTest {
        val airports = repository.getAirportsListFromFavorites()
            .first()
        assertEquals(airports, FakeDataSource.airportsListResult)
    }
}