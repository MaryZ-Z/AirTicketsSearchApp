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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.airticketssearchapp.R
import com.android.airticketssearchapp.ui.components.TransparentTextFieldWithIcons
import com.android.airticketssearchapp.ui.theme.Black
import com.android.airticketssearchapp.ui.theme.Grey3
import com.android.airticketssearchapp.ui.theme.Grey4
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
            onSwitchClick = { /*TODO*/ },
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
            .shadow(
                elevation = 4.dp,
                spotColor = Black,
                ambientColor = Grey6,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey3
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
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
                    .padding(horizontal = 8.dp)
            ) {
                TransparentTextFieldWithIcons(
                    value = fromText,
                    onValueChange = { },
                    placeholderResId = R.string.main_text_field_from_placeholder,
                    isEnabled = false,
                    leadingIconResId = null,
                    trailingIconResId = R.drawable.ic_switch,
                    onIconClick = onSwitchClick,
                    tint = White
                )
                HorizontalDivider(thickness = 1.dp, color = Grey5)
                TransparentTextFieldWithIcons(
                    value = toText,
                    onValueChange = {  },
                    placeholderResId = R.string.main_text_field_to_placeholder,
                    isEnabled = false,
                    leadingIconResId = null,
                    trailingIconResId = R.drawable.ic_clear,
                    onIconClick = onClearClick,
                    tint = Grey7
                )
            }
        }
    }
}