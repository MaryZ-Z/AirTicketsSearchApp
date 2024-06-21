package com.android.airticketssearchapp.extensions

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.android.airticketssearchapp.navigation.Screen

fun NavDestination?.currentScreen() = Screen.fromRoute(this?.route)

fun NavDestination?.isNotStartDestination() = this?.route != this?.parent?.startDestinationRoute

fun NavDestination?.hasRouteInHierarchy(route: String) =
    this?.hierarchy?.any { it.route == route } == true