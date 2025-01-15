package com.example.flightsearch

import com.example.flightsearch.data.favorite.FavoriteRepository
import org.junit.Test
import com.example.flightsearch.data.favorite.OfflineFavoriteRepository
import com.example.flightsearch.fake.FakeDataSource
import com.example.flightsearch.fake.FakeFavoriteDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before

/**
 * Тесты для [OfflineFavoriteRepository]
 */
class OfflineFavoriteRepositoryTest {

    private lateinit var repository: FavoriteRepository

    @Before
    fun createOfflineRepository() {
        repository = OfflineFavoriteRepository(FakeFavoriteDao())
    }

    @Test
    fun offlineFavoriteRepository_getFavoriteDestinationsFromAirport() = runTest {
        val destinations = repository.getFavoriteDestinationsFromAirport(
            FakeDataSource.departureCodeOne
        ).first()
        assertEquals(destinations, FakeDataSource.favoriteDestinations)
    }

    @Test
    fun offlineFavoriteRepository_getAllFavoriteFlights() = runTest {
        val destinations = repository.getAllFavoriteFlights().first()
        assertEquals(destinations, FakeDataSource.favoriteFlights)
    }
}