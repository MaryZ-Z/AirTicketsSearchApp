package com.android.airticketssearchapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.android.airticketssearchapp.R

val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 26.4.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.2.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = SFProDisplay,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.8.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.8.sp
    ),
    labelMedium = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.8.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 11.sp
    )
)