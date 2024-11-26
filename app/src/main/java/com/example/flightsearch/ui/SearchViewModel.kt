package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.Airport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

data class SearchUiState(
    val searchQueryText: String = "",
    val isSearching: Boolean = false,
    val suggestions: Flow<List<Airport>> = emptyFlow(),
    val currentAirport: Airport = Airport(),
    val destinations: Flow<List<Airport>> = emptyFlow()
)

class SearchViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    var uiState = _uiState.asStateFlow()

    fun updateSearchQueryText(queryText: String) {
        _uiState.update {
            it.copy(
                searchQueryText = queryText,
                isSearching = true,
                suggestions = getAirportSearchSuggestion(queryText)
            )
        }
    }

    private fun getAirportSearchSuggestion(queryText: String): Flow<List<Airport>> =
            if (queryText == "")
                flow {
                    emit(emptyList())
                }
            else
                TODO()



    fun showFlightsList(airportId: Int) {
        if (airportId == 0)
            _uiState.update {
                it.copy(
                    searchQueryText = TODO(),
                    currentAirport = TODO(),
                    destinations = TODO(),
                    isSearching = false
                )
            }
    }
}