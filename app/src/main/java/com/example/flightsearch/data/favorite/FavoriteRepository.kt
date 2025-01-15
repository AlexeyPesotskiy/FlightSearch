package com.example.flightsearch.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    /**
     * Save flight marked by user
     */
    suspend fun addFlight(favorite: Favorite)

    /**
     * Delete flight unmarked by user
     */
    suspend fun deleteFlight(departureCode: String, destinationCode: String)

    /**
     * Retrieve list of all flights marked as "favorite" from airport
     */
    fun getFavoriteDestinationsFromAirport(airportCode: String): Flow<List<String>>

    /**
     * Retrieve list of all flights marked as "favorite"
     */
    fun getAllFavoriteFlights(): Flow<List<Favorite>>
}