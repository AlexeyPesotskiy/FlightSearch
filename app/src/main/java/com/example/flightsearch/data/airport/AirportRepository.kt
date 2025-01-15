package com.example.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    /**
     * retrieve airports matching user query
     */
    fun getAirportsMatchingQuery(queryText: String): Flow<List<Airport>>

    /**
     * retrieve list of all flights destinations from the airport
     */
    fun getAllDestinationsFromAirport(airportCode: String): Flow<List<Airport>>

    /**
     * retrieve list of all airports(destination or departure) from list of favorite flights
     */
    fun getAirportsListFromFavorites(): Flow<List<Airport>>
}