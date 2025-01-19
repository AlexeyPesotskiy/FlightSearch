package com.example.flightsearch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportDao
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteDao

@Database(entities = [Airport::class, Favorite::class], version = 1)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
}