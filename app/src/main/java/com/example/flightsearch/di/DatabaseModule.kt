package com.example.flightsearch.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearch.data.FlightsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FlightsDatabase =
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