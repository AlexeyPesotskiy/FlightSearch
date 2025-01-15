package com.example.flightsearch.di

import android.content.Context
import com.example.flightsearch.data.airport.AirportRepository
import com.example.flightsearch.data.FlightsDatabase
import com.example.flightsearch.data.airport.OfflineAirportRepository
import com.example.flightsearch.data.favorite.OfflineFavoriteRepository
import com.example.flightsearch.data.favorite.FavoriteRepository

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(context: Context) : AppContainer {
    override val airportRepository = OfflineAirportRepository(
        FlightsDatabase.getDatabase(context).airportDao()
    )
    override val favoriteRepository = OfflineFavoriteRepository(
        FlightsDatabase.getDatabase(context).favoriteDao()
    )
}