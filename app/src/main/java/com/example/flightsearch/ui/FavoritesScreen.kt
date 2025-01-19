package com.example.flightsearch.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.flightsearch.R
import com.example.flightsearch.data.CardFlight
import com.example.flightsearch.ui.components.FlightsList
import com.example.flightsearch.ui.navigation.NavigationDestination
import kotlinx.coroutines.flow.StateFlow

object FavoritesDestination : NavigationDestination {
    override val route: String = "favorite"
    override val titleRes = R.string.favorites
    override val icon: ImageVector = Icons.Rounded.Favorite
}

/**
 * Contains a list of favorites flights.
 */
@Composable
fun FavoritesScreen(
    state: StateFlow<SearchUiState>,
    onClickFavoriteButton: (CardFlight) -> Unit,
    saveScrollPosition: (String, Pair<Int, Int>) -> Unit,
    route: String,
    modifier: Modifier = Modifier
) {
    val uiState = state.collectAsState().value
    FlightsList(
        uiState.favoriteFlights,
        onClickFavoriteButton,
        saveScrollPosition = { saveScrollPosition(route, it) },
        scrollPosition = uiState.scrollPosition[route] ?: Pair(0, 0)
    )
}