package com.example.flightsearch.data.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE " +
            "departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun delete(departureCode: String, destinationCode: String)

    @Query("SELECT * FROM favorite")
    fun selectAllFavoriteFlights(): Flow<List<Favorite>>

    @Query("SELECT destination_code FROM favorite WHERE departure_code = :departureCode")
    fun selectFavoriteDestinationsFromAirport(departureCode: String): Flow<List<String>>
}