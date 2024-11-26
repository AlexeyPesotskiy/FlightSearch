package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    /**
     * retrieve airports matching user query
     */
    fun getAirportsMatchingQuery(queryText: String): Flow<List<Airport>>

    /**
     * retrieve list of all flights from the airport
     */
    fun getAllFlightsFromAirport(airportId: Int): Flow<List<Airport>>
}