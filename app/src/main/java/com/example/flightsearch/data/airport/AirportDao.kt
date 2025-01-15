package com.example.flightsearch.data.airport

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT airport.id, iata_code, name, passengers FROM airport " +
            "INNER JOIN favorite ON iata_code = departure_code OR iata_code = destination_code")
    fun selectAirportsListFromFavorites(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code " +
            "LIKE :queryText || '%' OR name LIKE :queryText || '%'")
    fun selectAirportsMatchingQuery(queryText: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code <> :currentCode ORDER BY passengers DESC")
    fun selectAllAirportsExcludeCurrent(currentCode: String) : Flow<List<Airport>>
}