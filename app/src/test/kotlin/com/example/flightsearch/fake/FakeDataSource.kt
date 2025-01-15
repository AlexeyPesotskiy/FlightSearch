package com.example.flightsearch.fake

import com.example.flightsearch.data.airport.Airport
import com.example.flightsearch.data.favorite.Favorite
import kotlinx.coroutines.flow.flow

object FakeDataSource {
    const val departureCodeOne = "ABC"
    private const val destinationCodeOne = "XYZ"
    private const val destinationCodeTwo = "NLO"


    val favoriteFlights = listOf(
        Favorite(1, departureCodeOne, destinationCodeOne),
        Favorite(2, departureCodeOne, destinationCodeTwo)
    )

    val allFavoriteFlights = flow {
        emit(favoriteFlights)
    }

    val favoriteDestinations = listOf(
        destinationCodeOne, destinationCodeTwo
    )

    val allFavoriteDestinations = flow {
        emit(favoriteDestinations)
    }

    val airportsListResult = listOf(
        Airport(
            1,
            departureCodeOne,
            "first",
            10
        )
    )

    val selectAirportsResult = flow {
        emit(airportsListResult)
    }
}