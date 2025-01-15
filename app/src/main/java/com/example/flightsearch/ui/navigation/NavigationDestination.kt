package com.example.flightsearch.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Interface to describe the navigation destinations for the app
 */
interface NavigationDestination {
    /**
     * Unique name to define the path for a composable
     */
    val route: String

    /**
     * String resource id to that contains screen title.
     */
    val titleRes: Int

    /**
     * Image for bottom navigation bar item.
     */
    val icon: ImageVector
}