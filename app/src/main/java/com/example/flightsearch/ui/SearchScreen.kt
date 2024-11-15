package com.example.flightsearch.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.flightsearch.R
import com.example.flightsearch.ui.navigation.NavigationDestination

object SearchDestination : NavigationDestination {
    override val route: String = "search"
    override val titleRes = R.string.search
    override val icon: ImageVector = Icons.Rounded.Search
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {

}