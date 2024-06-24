package com.android.airticketssearchapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.airticketssearchapp.R
import com.android.airticketssearchapp.data.Hints
import com.android.airticketssearchapp.data.Offer
import com.android.airticketssearchapp.data.OfferImage
import com.android.airticketssearchapp.data.Popular
import com.android.airticketssearchapp.navigation.Screen
import com.android.airticketssearchapp.ui.components.TransparentTextField
import com.android.airticketssearchapp.ui.components.TransparentTextFieldWithIcons
import com.android.airticketssearchapp.ui.theme.Black
import com.android.airticketssearchapp.ui.theme.Grey2
import com.android.airticketssearchapp.ui.theme.Grey3
import com.android.airticketssearchapp.ui.theme.Grey4
import com.android.airticketssearchapp.ui.theme.Grey5
import com.android.airticketssearchapp.ui.theme.Grey6
import com.android.airticketssearchapp.ui.theme.Grey7
import com.android.airticketssearchapp.ui.theme.White

@Composable
fun MainScreen(navigate: (String) -> Unit) {
    val viewModel: MainViewModel = hiltViewModel()
    val from = viewModel.from
    val to = viewModel.to

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 28.dp)
    ) {
        Text(
            text = stringResource(id = R.string.main_title),
            style = MaterialTheme.typography.titleLarge,
            color = Grey7,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextFields(
            onClick = viewModel::showSearchBottomSheet,
            fromText = from,
            toText = to,
            onFromTextChange = viewModel::updateFrom,
            onToTextChange = viewModel::updateTo
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = stringResource(id = R.string.main_music_title),
            style = MaterialTheme.typography.titleLarge,
            color = Grey7,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(19.dp))
        RowOffers(
            items = viewModel.state.getIfSuccess()?.offers ?: emptyList()
        )
    }

    if (viewModel.showSearchBottomSheet) {
        SearchBottomSheet(
            onDismissClick = viewModel::hideSearchBottomSheet,
            onPopularClick = {
                viewModel.updateTo(it)
                navigate(Screen.SearchResult.navigate(from, it))
            },
            fromText = from,
            toText = to,
            onFromTextChange = viewModel::updateFrom,
            onToTextChange = viewModel::updateTo,
            onIconClick = viewModel::clearTo,
            navigate = navigate
        )
    }
}

@Composable
fun TextFields(
    onClick: () -> Unit,
    fromText: String,
    toText: String,
    onFromTextChange: (String) -> Unit,
    onToTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Grey3)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .shadow(
                    elevation = 4.dp,
                    spotColor = Black,
                    ambientColor = Grey6,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Grey4
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    TransparentTextField(
                        value = fromText,
                        onValueChange = onFromTextChange,
                        placeholderResId = R.string.main_text_field_from_placeholder,
                        isEnabled = true,
                        onClick = { }
                    )
                    HorizontalDivider(thickness = 1.dp, color = Grey5)
                    TransparentTextField(
                        value = toText,
                        onValueChange = onToTextChange,
                        placeholderResId = R.string.main_text_field_to_placeholder,
                        isEnabled = false,
                        onClick = onClick
                    )
                }
            }
        }
    }
}


@Composable
fun RowOffers(
    items: List<Offer>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(67.dp)
    ) {
        items(items) { item ->
            OfferCard(item = item)
        }
    }
}

@Composable
fun OfferCard(
    item: Offer
) {
    val offerImages = OfferImage.entries

    Column {
        Image(
            painter = painterResource(id = offerImages[item.id.dec()].imageResId),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .size(132.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.town,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_main),
                contentDescription = null,
                modifier = Modifier.size(17.dp),
                tint = Grey6
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(
                    id = R.string.main_offer_price,
                    item.price.value / 1000,
                    (item.price.value % 1000).toString().padStart(length = 3, padChar = '0')
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    onDismissClick: () -> Unit,
    onIconClick: () -> Unit,
    onPopularClick: (String) -> Unit,
    fromText: String,
    toText: String,
    onFromTextChange: (String) -> Unit,
    onToTextChange: (String) -> Unit,
    navigate: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissClick,
        sheetState = sheetState,
        tonalElevation = 0.dp,
        dragHandle = { DragHandle() },
        containerColor = Grey2
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Grey3
                )
            ) {
                TransparentTextFieldWithIcons(
                    value = fromText,
                    onValueChange = onFromTextChange,
                    placeholderResId = R.string.main_text_field_from_placeholder,
                    leadingIconResId = R.drawable.ic_plane,
                    trailingIconResId = null,
                    tint = Color.Unspecified,
                    onIconClick = { },
                    isEnabled = false
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Grey4
                )
                TransparentTextFieldWithIcons(
                    value = toText,
                    onValueChange = onToTextChange,
                    placeholderResId = R.string.main_text_field_to_placeholder,
                    leadingIconResId = R.drawable.ic_search,
                    trailingIconResId = R.drawable.ic_clear,
                    tint = White,
                    onIconClick = onIconClick,
                    isEnabled = true
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            RowHints(navigate = navigate, fromText = fromText)
            Spacer(modifier = Modifier.height(30.dp))
            Popular(onClick = onPopularClick)
        }
    }
}

@Composable
fun RowHints(
    navigate: (String) -> Unit,
    fromText: String
) {
    val hints = Hints.entries
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        hints.forEach { hint ->
            Hint(
                iconResId = hint.iconResId,
                textResId = hint.textResId,
                onClick = {
                    if (hint.ordinal == 1) {
                        navigate(Screen.SearchResult.navigate(
                            from = fromText,
                            to = context.getString(hint.textResId))
                        )
                    } else {
                        navigate(Screen.Empty.route)
                    }
                }
            )
        }
    }
}

@Composable
fun Hint(
    iconResId: Int,
    textResId: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = textResId),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Popular(
    onClick: (String) -> Unit
) {
    val populars = Popular.entries
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey3
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            populars.forEach { popular ->
                PopularRow(
                    onClick = { onClick(context.getString(popular.textResId)) },
                    imageResId = popular.imageResId,
                    textResId = popular.textResId
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Grey4
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PopularRow(
    onClick: () -> Unit,
    imageResId: Int,
    textResId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(id = textResId),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.main_popular_description),
                style = MaterialTheme.typography.bodySmall,
                color = Grey5
            )
        }
    }
}