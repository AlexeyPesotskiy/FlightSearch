package com.example.flightsearch

import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.fake.FakeAirportRepository
import com.example.flightsearch.fake.FakeDataSource
import com.example.flightsearch.fake.FakeFavoriteRepository
import com.example.flightsearch.ui.FlightSearchViewModel
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Тесты для [FlightSearchViewModel]
 */
class FlightSearchViewModelTest {

    private lateinit var viewModel: FlightSearchViewModel

    @Before
    fun createFlightSearchViewModel() {
        viewModel = FlightSearchViewModel(
            FakeAirportRepository(),
            FakeFavoriteRepository()
        )
    }

    @Test
    fun flightSearchViewModel_updateSearchQueryText_correctSearchQuery() = runTest {
        val newQueryText = "new query Text."
        viewModel.updateSearchQueryText(newQueryText)
        assertEquals(viewModel.uiState.value.searchQueryText, newQueryText)
        assertEquals(viewModel.uiState.value.isSearching, true)
        assertEquals(
            viewModel.uiState.value.suggestions.first(),
            FakeDataSource.selectAirportsResult.first()
        )
    }

    @Test
    fun flightSearchViewModel_updateSearchQueryText_emptySearchQuery() = runTest {
        val newQueryText = ""
        viewModel.updateSearchQueryText(newQueryText)
        assertEquals(viewModel.uiState.value.searchQueryText, newQueryText)
        assertEquals(viewModel.uiState.value.isSearching, true)
        assertEquals(viewModel.uiState.value.suggestions.first(), emptyList<Airport>())
    }

    @Test
    fun flightSearchViewModel_saveScrollPosition() {
        val screenId = "first"
        val position = Pair(0, 10)
        viewModel.saveScrollPosition(screenId, position)
        assertEquals(viewModel.uiState.value.scrollPosition[screenId], position)
    }

    @Test
    fun flightSearchViewModel_showFlightsList() = runTest {
        with(FakeDataSource) {
            val departureAirport = Airport(
                iataCode = departureCodeOne,
                name = departureNameOne
            )
            viewModel.showFlightsList(departureAirport)
            assertEquals(viewModel.uiState.value.searchQueryText, departureCodeOne)
            assertEquals(viewModel.uiState.value.isSearching, false)
            assertEquals(
                viewModel.uiState.value.flightsFromAirport.first(),
                showFlightsListResult.first()
            )
        }
    }
}