package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportRepository
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

class SearchViewModel(
    val repository: AirportRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    var uiState = _uiState.asStateFlow()

    fun updateSearchQueryText(queryText: String) =
        _uiState.update {
            it.copy(
                searchQueryText = queryText,
                isSearching = true,
                suggestions = getAirportSearchSuggestion(queryText)
            )
        }

    private fun getAirportSearchSuggestion(queryText: String): Flow<List<Airport>> =
        if (queryText.isEmpty())
            flow {
                emit(emptyList())
            }
        else
            repository.getAirportsMatchingQuery(queryText)


    fun showFlightsList(airport: Airport) =
        _uiState.update {
            it.copy(
                searchQueryText = airport.iataCode,
                currentAirport = airport,
                destinations = repository.getAllFlightsFromAirport(airport.id),
                isSearching = false
            )
        }

    companion object {
        val factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    repository = (this[APPLICATION_KEY] as FlightSearchApplication)
                        .container.airportRepository
                )
            }
        }
    }
}