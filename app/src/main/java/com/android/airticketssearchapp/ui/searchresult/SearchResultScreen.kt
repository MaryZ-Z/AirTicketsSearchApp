package com.android.airticketssearchapp.ui.searchresult

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.airticketssearchapp.R
import com.android.airticketssearchapp.ui.theme.Grey3
import com.android.airticketssearchapp.ui.theme.Grey5
import com.android.airticketssearchapp.ui.theme.Grey6
import com.android.airticketssearchapp.ui.theme.Grey7
import com.android.airticketssearchapp.ui.theme.White

@Composable
fun SearchResultScreen(
    navigate: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel: SearchResultViewModel = hiltViewModel()

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
            fromText = viewModel.localFrom,
            toText = viewModel.localTo
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
            .padding(all = 16.dp)
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