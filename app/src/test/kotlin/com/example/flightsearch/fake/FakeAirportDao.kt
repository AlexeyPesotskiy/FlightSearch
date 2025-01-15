package com.example.flightsearch.fake

import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportDao
import kotlinx.coroutines.flow.Flow

class FakeAirportDao : AirportDao {

    override fun selectAirportsListFromFavorites(): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult

    override fun selectAirportsMatchingQuery(queryText: String): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult

    override fun selectAllAirportsExcludeCurrent(currentCode: String): Flow<List<Airport>> =
        FakeDataSource.selectAirportsResult
}