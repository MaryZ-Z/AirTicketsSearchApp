package com.android.airticketssearchapp.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.android.airticketssearchapp.R

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int? = null,
    isTopBarVisible: Boolean = true
) {

    data object Empty : Screen(route = "empty")

    data object Main : Screen(route = "main")

    class SearchResult : Screen(route = route, isTopBarVisible = false) {
        companion object {
            private const val ROUTE = "search_result"
            const val FROM = "from"
            const val TO = "to"
            const val route = "$ROUTE/{$FROM}/{$TO}"
            val arguments = listOf(
                navArgument(FROM) { type = NavType.StringType },
                navArgument(TO) { type = NavType.StringType }
            )

            fun navigate(from: String, to: String) = "$ROUTE/$from/$to"
        }
    }

    data object Hotels : Screen(route = "hotels", titleResId = R.string.navigation_hotels_label)

    data object Shortly : Screen(route = "shortly", titleResId = R.string.navigation_shortly_label)

    data object Subscribes : Screen(route = "subscribes", titleResId = R.string.navigation_subscribes_label)

    data object Profile : Screen(route = "profile", titleResId = R.string.navigation_profile_label)

    companion object {
        fun fromRoute(route: String?): Screen =
            when (route) {
                Empty.route -> Empty
                Main.route -> Main
                SearchResult.route -> SearchResult()
                Hotels.route -> Hotels
                Shortly.route -> Shortly
                Subscribes.route -> Subscribes
                Profile.route -> Profile
                null -> Empty
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}