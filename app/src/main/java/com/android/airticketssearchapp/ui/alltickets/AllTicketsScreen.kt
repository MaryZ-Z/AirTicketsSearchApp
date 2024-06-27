package com.android.airticketssearchapp.ui.alltickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.airticketssearchapp.R
import com.android.airticketssearchapp.data.alltickets.Ticket
import com.android.airticketssearchapp.extensions.fullMonthNameWithDay
import com.android.airticketssearchapp.ui.common.UiState
import com.android.airticketssearchapp.ui.components.Loading
import com.android.airticketssearchapp.ui.theme.BlueForIcon
import com.android.airticketssearchapp.ui.theme.Grey1
import com.android.airticketssearchapp.ui.theme.Grey2
import com.android.airticketssearchapp.ui.theme.Grey6
import java.time.LocalDate

@Composable
fun AllTicketsScreen(navigateBack: () -> Unit) {
    val viewModel: AllTicketsViewModel = hiltViewModel()
    val returnDate = viewModel.returnDate
    val date = viewModel.date

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
        ) {
            TopRow(
                text = stringResource(
                    id = R.string.all_tickets_from_to,
                    viewModel.from,
                    viewModel.to
                ),
                textDates = if (returnDate != null) {
                    stringResource(
                        id = R.string.all_tickets_date_with_return,
                        LocalDate.parse(date).fullMonthNameWithDay(),
                        LocalDate.parse(returnDate).fullMonthNameWithDay()
                    )
                } else {
                    stringResource(
                        id = R.string.all_tickets_date,
                        LocalDate.parse(date).fullMonthNameWithDay()
                    )
                },
                navigateBack = navigateBack
            )
            when (val state = viewModel.state) {
                is UiState.Loading -> Loading()
                is UiState.Success -> Flights(tickets = state.data.tickets)
            }
        }
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                modifier = Modifier.padding(start = 10.dp),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(id = R.string.all_tickets_filter),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_prices),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(id = R.string.all_tickets_prices),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun TopRow(
    text: String,
    textDates: String,
    navigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Grey2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = navigateBack) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = BlueForIcon
            )
        }
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = textDates,
                style = MaterialTheme.typography.headlineSmall,
                color = Grey6
            )
        }
    }
}

@Composable
fun Flights(
    tickets: List<Ticket>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 26.dp, bottom = 16.dp)
    ) {
        items(tickets) { ticket ->
            FlightCard(item = ticket)
        }
    }
}

@Composable
fun FlightCard(
    item: Ticket
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .background(color = Grey1, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = stringResource(
                    id = R.string.search_result_price,
                    item.price.value / 1000,
                    (item.price.value % 1000).toString().padStart(length = 3, padChar = '0')
                ),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = if (item.badge != null) 21.dp else 16.dp
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 23.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(7.dp))
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = item.departureTime.toString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = stringResource(id = R.string.all_tickets_dash),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Grey6
                        )
                        Text(
                            text = item.arrivalTime.toString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = stringResource(
                                id = R.string.all_tickets_time_on_way,
                                item.timeOnWayString
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                        if (!item.hasTransfer) {
                            Text(
                                text = stringResource(id = R.string.all_tickets_slash),
                                style = MaterialTheme.typography.bodySmall,
                                color = Grey6
                            )
                            Text(
                                text = stringResource(id = R.string.all_tickets_without_transfer),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = item.departure.airport,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Grey6
                        )
                        Spacer(modifier = Modifier.width(25.dp))
                        Text(
                            text = item.arrival.airport,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Grey6
                        )
                    }
                }
            }
        }
        if (item.badge != null) {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = item.badge,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp)
                )
            }
        }
    }
}