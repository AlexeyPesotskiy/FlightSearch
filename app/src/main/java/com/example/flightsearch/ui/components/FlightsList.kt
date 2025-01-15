package com.example.flightsearch.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.flightsearch.R
import com.example.flightsearch.data.CardFlight
import com.example.flightsearch.ui.theme.IsFavorite
import com.example.flightsearch.ui.theme.NotFavorite
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun FlightsList(
    flightsListStream: Flow<List<CardFlight>>,
    onClickFavoriteButton: (CardFlight) -> Unit,
    modifier: Modifier = Modifier,
    saveScrollPosition: (String, Pair<Int, Int>) -> Unit = { _, _ -> },
    route: String,
    scrollPosition: MutableMap<String, Pair<Int, Int>>
) {
    val destinationsList by flightsListStream.collectAsState(emptyList())
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition[route]?.first ?: 0,
        initialFirstVisibleItemScrollOffset = scrollPosition[route]?.second ?: 0
    )

    /*
    Сохранение позиции скрола, через 250мс после последнего взаимодействия,
    для избежания слишком частого сохранения
    */
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex
        }.combine(snapshotFlow {
            listState.firstVisibleItemScrollOffset
        }) { index, offset ->
            Pair(index, offset)
        }.debounce(250).collectLatest {
            saveScrollPosition(route, it)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        items(items = destinationsList) {
            FlightCard(it, onClickFavoriteButton)
        }
    }
}

@Composable
fun FlightCard(
    flight: CardFlight,
    onClickFavoriteButton: (CardFlight) -> Unit,
) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.small_padding))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.small_padding))
                .fillMaxWidth()
        ) {
            Column(
                Modifier.weight(4f)
            ) {
                Text(text = stringResource(R.string.depart))
                AirportCodeWithName(flight.fromIataCode, flight.fromName)
                Text(text = stringResource(R.string.arrive))
                AirportCodeWithName(flight.toIataCode, flight.toName)
            }
            IconButton(
                onClick = {
                    onClickFavoriteButton(flight)
                },
                modifier = Modifier
                    .size(dimensionResource(R.dimen.card_icon_favorite_size))
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (flight.isFavorite) IsFavorite else NotFavorite,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AirportCodeWithName(
    code: String,
    name: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small_padding))
    ) {
        Text(
            text = code,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}