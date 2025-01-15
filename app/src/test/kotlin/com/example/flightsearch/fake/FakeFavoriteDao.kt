package com.example.flightsearch.fake

import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteDao
import kotlinx.coroutines.flow.Flow

class FakeFavoriteDao : FavoriteDao {
    override suspend fun insert(favorite: Favorite) {}

    override suspend fun delete(departureCode: String, destinationCode: String) {}

    override fun selectAllFavoriteFlights(): Flow<List<Favorite>> =
        FakeDataSource.allFavoriteFlights

    override fun selectFavoriteDestinationsFromAirport(departureCode: String): Flow<List<String>> =
        FakeDataSource.allFavoriteDestinations
}