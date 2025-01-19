package com.example.flightsearch.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserPreferencesRepository {
    private companion object {
        val LAST_SEARCH_QUERY = stringPreferencesKey("last_search_query")
    }

    override val lastSearchQuery: Flow<String> = dataStore.data
        .catch {
            if (it is IOException)
                emit(emptyPreferences())
            else
                throw it
        }
        .map {
            it[LAST_SEARCH_QUERY] ?: ""
        }

    override suspend fun saveLastSearchQuery(lastSearchQuery: String) {
        dataStore.edit {
            it[LAST_SEARCH_QUERY] = lastSearchQuery
        }
    }
}