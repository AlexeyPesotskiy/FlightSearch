package com.example.flightsearch.di

import com.example.flightsearch.ui.FlightSearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun flightSearchViewModel(): FlightSearchViewModel
}