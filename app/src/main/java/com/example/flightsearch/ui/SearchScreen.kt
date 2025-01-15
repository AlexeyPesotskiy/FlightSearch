package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.flightsearch.R
import com.example.flightsearch.data.CardFlight
import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.ui.components.FlightsList
import com.example.flightsearch.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.StateFlow


object SearchDestination : NavigationDestination {
    override val route: String = "search"
    override val titleRes = R.string.search
    override val icon: ImageVector = Icons.Rounded.Search
}

/**
 * Contains a search field with a drop-down list of search
 * suggestions and a list of flights.
 */
@Composable
fun SearchScreen(
    state: StateFlow<SearchUiState>,
    onQueryChange: (String) -> Unit,
    onSearch: (Airport) -> Unit,
    onClickFavoriteButton: (CardFlight) -> Unit,
    saveScrollPosition: (String, Pair<Int, Int>) -> Unit,
    route: String,
    modifier: Modifier = Modifier
) {
    Column {
        SearchField(state, onQueryChange, onSearch, modifier)
        val uiState = state.collectAsState().value
        if (!uiState.isSearching && uiState.searchQueryText.isNotEmpty())
            FlightsList(
                uiState.flightsFromAirport,
                onClickFavoriteButton,
                modifier,
                saveScrollPosition,
                route = route,
                scrollPosition = uiState.scrollPosition
            )
    }
}

/**
 * Search field with a drop-down list of search suggestions
 */
@Composable
fun SearchField(
    state: StateFlow<SearchUiState>,
    onQueryChange: (String) -> Unit,
    onSearch: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = state.collectAsState().value
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = uiState.searchQueryText,
            onValueChange = onQueryChange,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = null
                )
            },
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            placeholder = {
                if (uiState.searchQueryText.isBlank())
                    Text(stringResource(R.string.search_placeholder))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.medium_padding))
        )
        val suggestionsList by uiState.suggestions.collectAsState(emptyList())
        if (uiState.isSearching)
            SearchSuggestionsList(suggestionsList, onSearch)
    }
}

/**
 * List of search suggestions
 */
@Composable
fun SearchSuggestionsList(
    suggestionsList: List<Airport>,
    onSearch: (Airport) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = suggestionsList) {
            Row(
                horizontalArrangement = Arrangement
                    .spacedBy(dimensionResource(R.dimen.text_padding)),
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.small_padding))
                    .clickable {
                        onSearch(it)
                    }
            ) {
                Text(
                    text = it.iataCode,
                    fontWeight = FontWeight.Bold
                )
                Text(text = it.name)
            }
        }
    }
}