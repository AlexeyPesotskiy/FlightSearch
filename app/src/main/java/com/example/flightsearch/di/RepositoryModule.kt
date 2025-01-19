package com.example.flightsearch.di

import com.example.flightsearch.data.airport.AirportRepository
import com.example.flightsearch.data.airport.OfflineAirportRepository
import com.example.flightsearch.data.favorite.FavoriteRepository
import com.example.flightsearch.data.favorite.OfflineFavoriteRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideAirportRepository(
        repository: OfflineAirportRepository
    ): AirportRepository

    @Binds
    abstract fun provideFavoriteRepository(
        repository: OfflineFavoriteRepository
    ): FavoriteRepository
}