package com.example.flightsearch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch.ui.navigation.BottomNavigationBar
import com.example.flightsearch.ui.navigation.FlightSearchNavHost

@Composable
fun FlightSearchApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        FlightSearchNavHost(
            navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}