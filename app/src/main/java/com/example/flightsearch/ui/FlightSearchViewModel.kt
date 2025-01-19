package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.CardFlight
import com.example.flightsearch.data.preferences.UserPreferencesRepository
import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.airport.AirportRepository
import com.example.flightsearch.data.favorite.FavoriteRepository
import com.example.flightsearch.data.mapToFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val searchQueryText: String = "",
    val isSearching: Boolean = false,
    val suggestions: Flow<List<Airport>> = emptyFlow(),
    val flightsFromAirport: Flow<List<CardFlight>> = emptyFlow(),
    val favoriteFlights: Flow<List<CardFlight>> = emptyFlow(),
    val scrollPosition: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
)

@HiltViewModel
class FlightSearchViewModel @Inject constructor(
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    /**
     * Сложность получения данных карточек избранных рейсов,
     * связана с особенностями хранения информации в предоставленной БД
     * 1. Получаем поток списка избранных рейсов (Код отправления, Код назначения)
     * 2. Смешиваем с потоком аэропортов из списка избранных рейсов
     * 3. Модифицируем поток избранных рейсов, в поток карточек с информацие о рейсе
     * (Код отправления, название, Код назначения, название)
     */
    private val _uiState = MutableStateFlow(SearchUiState(
        favoriteFlights = favoriteRepository
            .getAllFavoriteFlights()
            .combine(
                airportRepository.getAirportsListFromFavorites().map { list ->
                    list.associate { it.iataCode to it.name }
                }
            ) { favorites, airports ->
                favorites.map {
                    CardFlight(
                        it.departureCode,
                        airports[it.departureCode] ?: "",
                        it.destinationCode,
                        airports[it.destinationCode] ?: "",
                        true
                    )
                }
            }
    ))
    var uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            updateSearchQueryText(
                userPreferencesRepository.lastSearchQuery.first()
            )
        }
    }

    private fun saveSearchQueryInPreferences(queryText: String) =
        viewModelScope.launch {
            userPreferencesRepository.saveLastSearchQuery(queryText)
        }

    fun updateSearchQueryText(queryText: String) {
        saveSearchQueryInPreferences(queryText)
        _uiState.update {
            it.copy(
                searchQueryText = queryText,
                isSearching = true,
                suggestions = getAirportSearchSuggestion(queryText)
            )
        }
    }

    private fun getAirportSearchSuggestion(queryText: String): Flow<List<Airport>> =
        if (queryText.isEmpty()) flow {
            emit(emptyList())
        }
        else airportRepository.getAirportsMatchingQuery(queryText)

    fun saveScrollPosition(screenId: String, position: Pair<Int, Int>) {
        _uiState.value.scrollPosition[screenId] = position
    }

    fun showFlightsList(fromAirport: Airport) = with(fromAirport) {
        saveSearchQueryInPreferences(iataCode)
        _uiState.update { state ->
            state.copy(
                searchQueryText = iataCode,
                isSearching = false,
//                Use combine to make downstream flow reflect changes in any upstream flow
                flightsFromAirport = favoriteRepository
                    .getFavoriteDestinationsFromAirport(iataCode)
                    .combine(
                        airportRepository.getAllDestinationsFromAirport(iataCode)
                    ) { favorites, destinations ->
                        destinations.map { toAirport ->
                            CardFlight(iataCode,
                                name,
                                toAirport.iataCode,
                                toAirport.name,
                                favorites.any { it == toAirport.iataCode })
                        }
                    }
            )
        }
    }

    fun toggleFavoriteButton(flight: CardFlight) {
        viewModelScope.launch {
            if (flight.isFavorite) favoriteRepository.deleteFlight(
                flight.fromIataCode,
                flight.toIataCode
            )
            else favoriteRepository.addFlight(flight.mapToFavorite())
        }
    }
}