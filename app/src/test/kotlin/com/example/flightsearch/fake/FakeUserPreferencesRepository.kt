package com.example.flightsearch.fake

import com.example.flightsearch.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.emptyFlow

class FakeUserPreferencesRepository : UserPreferencesRepository {
    override val lastSearchQuery = emptyFlow<String>()
    override suspend fun saveLastSearchQuery(lastSearchQuery: String) {}
}