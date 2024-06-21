package com.android.airticketssearchapp.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.android.airticketssearchapp.R

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int? = null
) {

    data object Empty : Screen(route = "empty")

    data object Main : Screen(route = "main")

    class CameraStream : Screen(
        route = route
    ) {
        companion object {
            private const val ROUTE = "camera_stream"
            const val NAME = "name"
            const val LOCATION = "location"
            const val RTSP_LINK = "rtspLink"
            const val route = "$ROUTE/{$NAME}/{$LOCATION}/{$RTSP_LINK}"
            val arguments = listOf(
                navArgument(NAME) { type = NavType.StringType }
            )

            //fun navigate(name: String, location: String, rtspLink: String) =
                //"$ROUTE/${name.encodeForNavigation()}/${location.encodeForNavigation()}/${rtspLink.encodeForNavigation()}"
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
                Hotels.route -> Hotels
                Shortly.route -> Shortly
                Subscribes.route -> Subscribes
                Profile.route -> Profile
                CameraStream.route -> CameraStream()
                null -> Empty
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}