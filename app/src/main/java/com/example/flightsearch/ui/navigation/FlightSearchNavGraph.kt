package com.example.flightsearch.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.R
import com.example.flightsearch.ui.FavoritesDestination
import com.example.flightsearch.ui.FavoritesScreen
import com.example.flightsearch.ui.SearchDestination
import com.example.flightsearch.ui.SearchScreen
import com.example.flightsearch.ui.SearchViewModel

/**
 * Provides Navigation graph for the application
 */
@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory)
    NavHost(
        navController = navController,
        startDestination = SearchDestination.route,
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.medium_padding))
    ) {
        composable(route = SearchDestination.route) {
            SearchScreen(
                viewModel.uiState,
                viewModel::updateSearchQueryText,
                viewModel::showFlightsList
            )
        }
        composable(route = FavoritesDestination.route) {
            FavoritesScreen()
        }
    }
}