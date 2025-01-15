package com.example.flightsearch.data.airport

import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(
    private val airportDao: AirportDao
) : AirportRepository {

    override fun getAirportsMatchingQuery(queryText: String): Flow<List<Airport>> =
        airportDao.selectAirportsMatchingQuery(queryText)

    override fun getAllDestinationsFromAirport(airportCode: String): Flow<List<Airport>> =
        airportDao.selectAllAirportsExcludeCurrent(airportCode)

    override fun getAirportsListFromFavorites(): Flow<List<Airport>> =
        airportDao.selectAirportsListFromFavorites()
}