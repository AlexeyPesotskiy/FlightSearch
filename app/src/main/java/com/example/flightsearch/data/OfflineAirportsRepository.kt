package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(
    private val airportDao: AirportDao
) : AirportRepository {
    override fun getAirportsMatchingQuery(queryText: String): Flow<List<Airport>> =
        airportDao.selectAirportsMatchingQuery(queryText)

    override fun getAllFlightsFromAirport(airportId: Int): Flow<List<Airport>> =
        airportDao.selectAllAirportsExcludeCurrent(airportId)
}