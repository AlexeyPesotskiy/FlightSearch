package com.example.flightsearch.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearch.data.FlightsDatabase
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): FlightsDatabase =
        Room.databaseBuilder(context, FlightsDatabase::class.java, "flights_database")
            .createFromAsset("database/flight_search.db")
            .build()

    @Provides
    @Singleton
    fun provideAirportDao(db: FlightsDatabase) = db.airportDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(db: FlightsDatabase) = db.favoriteDao()
}