package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport WHERE iata_code " +
            "LIKE :queryText || '%' OR name LIKE :queryText || '%'")
    fun selectAirportsMatchingQuery(queryText: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE id <> :currentId ORDER BY passengers DESC")
    fun selectAllAirportsExcludeCurrent(currentId: Int) : Flow<List<Airport>>
}