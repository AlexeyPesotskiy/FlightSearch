package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.di.ApplicationComponent
import com.example.flightsearch.di.DaggerApplicationComponent
import com.example.flightsearch.di.DatabaseModule

class FlightSearchApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}