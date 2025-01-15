package com.example.flightsearch.data

import com.example.flightsearch.data.favorite.Favorite

data class CardFlight(
    val fromIataCode: String = "",
    val fromName: String = "",
    val toIataCode: String = "",
    val toName: String = "",
    val isFavorite: Boolean = false
)

fun CardFlight.mapToFavorite() =
    Favorite(departureCode = fromIataCode, destinationCode = toIataCode)