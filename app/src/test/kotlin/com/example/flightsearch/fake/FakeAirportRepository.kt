package com.example.flightsearch.fake

import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportRepository
import kotlinx.coroutines.flow.Flow

class FakeAirportRepository : AirportRepository {
    override fun getAirportsMatchingQuery(queryText: String): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult

    override fun getAllDestinationsFromAirport(airportCode: String): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult

    override fun getAirportsListFromFavorites(): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult
}