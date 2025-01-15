package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportDao
import com.example.flightsearch.data.favorite.Favorite
import com.example.flightsearch.data.favorite.FavoriteDao

@Database(entities = [Airport::class, Favorite::class], version = 1)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FlightsDatabase? = null

        fun getDatabase(context: Context): FlightsDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(context, FlightsDatabase::class.java, "flights_database")
                .createFromAsset("database/flight_search.db")
                .build()
                .also { instance = it }
        }
    }
}