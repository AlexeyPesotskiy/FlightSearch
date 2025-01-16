package com.example.flightsearch.fake

import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FakeFavoriteRepository : FavoriteRepository {

    override suspend fun addFlight(favorite: Favorite) {}

    override suspend fun deleteFlight(departureCode: String, destinationCode: String) {}

    override fun getAllFavoriteFlights(): Flow<List<Favorite>> =
        FakeDataSource.allFavoriteFlights

    override fun getFavoriteDestinationsFromAirport(airportCode: String): Flow<List<String>> =
        FakeDataSource.allFavoriteDestinations
}