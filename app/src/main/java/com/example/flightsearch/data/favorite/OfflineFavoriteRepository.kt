package com.example.flightsearch.data.favorite


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {
    override suspend fun addFlight(favorite: Favorite) =
        favoriteDao.insert(favorite)

    override suspend fun deleteFlight(departureCode: String, destinationCode: String) =
        favoriteDao.delete(departureCode, destinationCode)

    override fun getFavoriteDestinationsFromAirport(airportCode: String): Flow<List<String>> =
        favoriteDao.selectFavoriteDestinationsFromAirport(airportCode)

    override fun getAllFavoriteFlights(): Flow<List<Favorite>> =
        favoriteDao.selectAllFavoriteFlights()
}