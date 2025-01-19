package com.example.flightsearch.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.R
import com.example.flightsearch.ui.FavoritesDestination
import com.example.flightsearch.ui.FavoritesScreen
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.SearchDestination
import com.example.flightsearch.ui.SearchScreen

/**
 * Provides Navigation graph for the application
 */
@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: FlightSearchViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = SearchDestination.route,
        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.medium_padding))
    ) {
        composable(route = SearchDestination.route) {
            SearchScreen(
                viewModel.uiState,
                viewModel::updateSearchQueryText,
                viewModel::showFlightsList,
                viewModel::toggleFavoriteButton,
                viewModel::saveScrollPosition,
                navController.currentDestination?.route.toString()
            )
        }
        composable(route = FavoritesDestination.route) {
            FavoritesScreen(
                viewModel.uiState,
                viewModel::toggleFavoriteButton,
                viewModel::saveScrollPosition,
                navController.currentDestination?.route.toString()
            )
        }
    }
}