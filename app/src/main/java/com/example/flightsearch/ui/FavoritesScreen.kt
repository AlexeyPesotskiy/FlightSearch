package com.example.flightsearch.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.flightsearch.R
import com.example.flightsearch.ui.navigation.NavigationDestination

object FavoritesDestination : NavigationDestination {
    override val route: String = "favorite"
    override val titleRes = R.string.favorites
    override val icon: ImageVector = Icons.Rounded.Favorite
}

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {

}