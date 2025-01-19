package com.example.flightsearch.data.preferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val lastSearchQuery: Flow<String>
    suspend fun saveLastSearchQuery(lastSearchQuery: String)
}