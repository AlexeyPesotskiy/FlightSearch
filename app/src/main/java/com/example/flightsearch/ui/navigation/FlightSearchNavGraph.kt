package com.example.flightsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.ui.FavoritesDestination
import com.example.flightsearch.ui.FavoritesScreen
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
    NavHost(
        navController = navController,
        startDestination = SearchDestination.route,
        modifier = modifier
    ) {
        composable(route = SearchDestination.route) {
            SearchScreen()
        }
        composable(route = FavoritesDestination.route) {
            FavoritesScreen()
        }
    }
}