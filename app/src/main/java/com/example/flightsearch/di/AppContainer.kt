package com.example.flightsearch.di

import android.content.Context
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.FlightsDatabase
import com.example.flightsearch.data.OfflineAirportsRepository

interface AppContainer {
    val airportRepository: AirportRepository
}

class AppDataContainer(context: Context) : AppContainer {
    override val airportRepository = OfflineAirportsRepository(
        FlightsDatabase.getDatabase(context).airportDao()
    )
}