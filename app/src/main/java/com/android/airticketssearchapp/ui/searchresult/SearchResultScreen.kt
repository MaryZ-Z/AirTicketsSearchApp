package com.android.airticketssearchapp.ui.searchresult

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.airticketssearchapp.R
import com.android.airticketssearchapp.data.SearchResponse
import com.android.airticketssearchapp.data.TicketsOffers
import com.android.airticketssearchapp.extensions.abbreviationWeek
import com.android.airticketssearchapp.extensions.monthNameWithDay
import com.android.airticketssearchapp.navigation.Screen
import com.android.airticketssearchapp.ui.common.UiState
import com.android.airticketssearchapp.ui.components.Button
import com.android.airticketssearchapp.ui.components.Loading
import com.android.airticketssearchapp.ui.theme.Blue
import com.android.airticketssearchapp.ui.theme.Grey1
import com.android.airticketssearchapp.ui.theme.Grey3
import com.android.airticketssearchapp.ui.theme.Grey5
import com.android.airticketssearchapp.ui.theme.Grey6
import com.android.airticketssearchapp.ui.theme.Grey7
import com.android.airticketssearchapp.ui.theme.White
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    navigate: (String) -> Unit, navigateBack: () -> Unit
) {
    val viewModel: SearchResultViewModel = hiltViewModel()
    val datePickerState = rememberDatePickerState()
    val from = viewModel.localFrom
    val to = viewModel.localTo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        TopCard(
            onClearClick = {
                viewModel.clearTo()
                navigateBack()
            },
            onBackClick = navigateBack,
            onSwitchClick = viewModel::switchFromTo,
            fromText = from,
            toText = to
        )
        ChipGroup(
            date = viewModel.date,
            returnDate = viewModel.returnDate,
            onReturnDateClick = viewModel::showReturnDatePickerDialog,
            onDateClick = viewModel::showDatePickerDialog
        )
        Spacer(modifier = Modifier.height(6.dp))
        when (val state = viewModel.state) {
            is UiState.Success -> SearchResult(items = state.data)
            is UiState.Loading -> Loading()
        }
        Spacer(modifier = Modifier.height(23.dp))
        Button(
            textResId = R.string.search_result_button,
            onClick = {
                navigate(Screen.AllTickets.navigate(
                    from = from,
                    to = to,
                    date = viewModel.date.toString(),
                    returnDate = viewModel.returnDate?.toString()
                ))
            },
            color = MaterialTheme.colorScheme.primary
        )
    }

    if (viewModel.isDatePickerDialogShowing) {
        DatePicker(
            onDismiss = viewModel::hideDatePickerDialog,
            onConfirmButton = { viewModel.updateDate(datePickerState.selectedDateMillis) },
            state = datePickerState
        )
    }

    if (viewModel.isReturnDatePickerDialogShowing) {
        DatePicker(
            onDismiss = viewModel::hideReturnDatePickerDialog,
            onConfirmButton = { viewModel.updateReturnDate(datePickerState.selectedDateMillis) },
            state = datePickerState
        )
    }
}

@Composable
fun TopCard(
    onClearClick: () -> Unit,
    onBackClick: () -> Unit,
    onSwitchClick: () -> Unit,
    fromText: String,
    toText: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 47.dp, bottom = 7.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey3
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TransparentTextFieldWithIcons(
                    value = fromText,
                    onValueChange = { },
                    placeholderResId = R.string.main_text_field_from_placeholder,
                    isEnabled = false,
                    trailingIconResId = R.drawable.ic_switch,
                    onIconClick = onSwitchClick,
                    tint = White
                )
                HorizontalDivider(thickness = 1.dp, color = Grey5)
                TransparentTextFieldWithIcons(
                    value = toText,
                    onValueChange = { },
                    placeholderResId = R.string.main_text_field_to_placeholder,
                    isEnabled = false,
                    trailingIconResId = R.drawable.ic_clear,
                    onIconClick = onClearClick,
                    tint = Grey7
                )
            }
        }
    }
}

@Composable
fun ChipGroup(
    date: LocalDate,
    returnDate: LocalDate?,
    onReturnDateClick: () -> Unit,
    onDateClick: () -> Unit
) {
    //todo не красится текст в чипсах, разобраться потом
    val departureDate: AnnotatedString = buildAnnotatedString {
        val fullText = stringResource(
            id = R.string.search_result_chip_date,
            date.monthNameWithDay(),
            date.dayOfWeek.abbreviationWeek()
        )
        val dayOfWeek = stringResource(
            id = R.string.search_result_chip_date_annotated, date.dayOfWeek.abbreviationWeek()
        )
        val dayOfWeekStartIndex = fullText.indexOf(dayOfWeek)
        val dayOfWeekEndIndex = dayOfWeekStartIndex + dayOfWeek.length
        withStyle(
            style = SpanStyle(color = White)
        ) {
            append(fullText)
        }
        addStyle(
            style = SpanStyle(color = Grey6), start = dayOfWeekStartIndex, end = dayOfWeekEndIndex
        )
    }

    val returnTicketDate: AnnotatedString = buildAnnotatedString {
        val fullText = stringResource(
            id = R.string.search_result_chip_date,
            returnDate?.monthNameWithDay() ?: "",
            returnDate?.dayOfWeek?.abbreviationWeek() ?: ""
        )
        val dayOfWeek = stringResource(
            id = R.string.search_result_chip_date_annotated,
            returnDate?.dayOfWeek?.abbreviationWeek() ?: ""
        )
        val dayOfWeekStartIndex = fullText.indexOf(dayOfWeek)
        val dayOfWeekEndIndex = dayOfWeekStartIndex + dayOfWeek.length
        withStyle(
            style = SpanStyle(color = White)
        ) {
            append(fullText)
        }
        addStyle(
            style = SpanStyle(color = Grey6),
            start = dayOfWeekStartIndex,
            end = dayOfWeekEndIndex
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            Chip(
                label = (if (returnDate == null) {
                    stringResource(id = R.string.search_result_chip_return_date)
                } else {
                    returnTicketDate.text
                }),
                onClick = onReturnDateClick,
                iconResId = if (returnDate == null) R.drawable.ic_plus else null
            )
        }
        item {
            Chip(
                label = departureDate.text,
                onClick = onDateClick,
                iconResId = null
            )
        }
        item {
            Chip(
                label = stringResource(id = R.string.search_result_chip_passenger),
                onClick = { },
                iconResId = R.drawable.ic_person_small,
                isEnabled = false
            )
        }
        item {
            Chip(
                label = stringResource(id = R.string.search_result_chip_filter),
                onClick = { },
                iconResId = R.drawable.ic_filter,
                isEnabled = false
            )
        }
    }
}

@Composable
fun Chip(
    label: String,
    onClick: () -> Unit,
    iconResId: Int?,
    isEnabled: Boolean = true
) {
    AssistChip(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Unspecified
            )
        },
        leadingIcon = {
            if (iconResId != null) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        },
        enabled = isEnabled,
        border = null,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Grey3,
            disabledContainerColor = Grey3,
            disabledLabelColor = White
        )
    )
}

@Composable
fun SearchResult(
    items: TicketsOffers
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey1
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.search_result_title),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            items.ticketsOffers.forEach {
                SearchResultRow(
                    item = it, tint = when (it.id) {
                        10 -> Blue
                        30 -> White
                        else -> Color.Unspecified
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = Grey5)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SearchResultRow(
    item: SearchResponse,
    tint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { })
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title, style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        id = R.string.search_result_price,
                        item.price.value / 1000,
                        (item.price.value % 1000).toString().padStart(length = 3, padChar = '0')
                    ),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                item.timeRange.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
fun TransparentTextFieldWithIcons(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderResId: Int,
    trailingIconResId: Int?,
    onIconClick: () -> Unit,
    tint: Color,
    isEnabled: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelMedium,
        modifier = Modifier.fillMaxWidth(),
        enabled = isEnabled,
        placeholder = {
            Text(text = stringResource(id = placeholderResId))
        },
        trailingIcon = {
            if (trailingIconResId != null) {
                IconButton(onClick = onIconClick) {
                    Icon(
                        painter = painterResource(id = trailingIconResId),
                        contentDescription = null,
                        tint = tint
                    )
                }
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Grey3,
            unfocusedContainerColor = Grey3,
            disabledContainerColor = Grey3,
            focusedTextColor = White,
            unfocusedTextColor = White,
            disabledTextColor = White,
            focusedPlaceholderColor = Grey6,
            unfocusedPlaceholderColor = Grey6,
            disabledPlaceholderColor = Grey6,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onDismiss: () -> Unit,
    onConfirmButton: () -> Unit,
    state: DatePickerState?
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirmButton) {
                Text(
                    text = stringResource(id = R.string.search_result_date_picker_dialog_confirm_button),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.search_result_date_picker_dialog_dismiss_button),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        content = {
            if (state != null) {
                DatePicker(state = state)
            }
        })
}