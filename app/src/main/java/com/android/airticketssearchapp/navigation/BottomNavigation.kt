package com.android.airticketssearchapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.airticketssearchapp.R

enum class BottomNavigation(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val labelResId: Int,
    @StringRes val descriptionResId: Int
) {
    Main(
        route = "main_tab",
        iconResId = R.drawable.ic_main,
        labelResId = R.string.navigation_main_label,
        descriptionResId = R.string.navigation_main_description
    ),
    Hotels(
        route = "hotels_tab",
        iconResId = R.drawable.ic_hotels,
        labelResId = R.string.navigation_hotels_label,
        descriptionResId = R.string.navigation_hotels_description
    ),
    Shortly(
        route = "shortly_tab",
        iconResId = R.drawable.ic_shortly,
        labelResId = R.string.navigation_shortly_label,
        descriptionResId = R.string.navigation_shortly_description
    ),
    Subscribes(
        route = "subscribes_tab",
        iconResId = R.drawable.ic_subscribes,
        labelResId = R.string.navigation_subscribes_label,
        descriptionResId = R.string.navigation_subscribes_description
    ),
    Profile(
        route = "profile_tab",
        iconResId = R.drawable.ic_profile,
        labelResId = R.string.navigation_profile_label,
        descriptionResId = R.string.navigation_profile_description
    )
}