package com.android.airticketssearchapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.android.airticketssearchapp.extensions.currentScreen
import com.android.airticketssearchapp.extensions.hasRouteInHierarchy
import com.android.airticketssearchapp.navigation.BottomNavigation
import com.android.airticketssearchapp.navigation.Screen
import com.android.airticketssearchapp.ui.common.UiError
import com.android.airticketssearchapp.ui.empty.EmptyScreen
import com.android.airticketssearchapp.ui.main.MainScreen
import com.android.airticketssearchapp.ui.searchresult.SearchResultScreen
import com.android.airticketssearchapp.ui.theme.AirTicketsSearchAppTheme
import com.android.airticketssearchapp.ui.theme.Black
import com.android.airticketssearchapp.ui.theme.Blue
import com.android.airticketssearchapp.ui.theme.Grey1
import com.android.airticketssearchapp.ui.theme.Grey3
import com.android.airticketssearchapp.ui.theme.Grey4
import com.android.airticketssearchapp.ui.theme.Grey5
import com.android.airticketssearchapp.ui.theme.Grey6
import com.android.airticketssearchapp.ui.theme.Grey7
import com.android.airticketssearchapp.ui.theme.White

@Composable
fun AirTicketsSearchApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val currentScreen = currentDestination.currentScreen()

    AirTicketsSearchAppTheme {
        val insets = ScaffoldDefaults.contentWindowInsets.union(WindowInsets.ime)

        Scaffold(
            topBar = {
                Column {
                    AnimatedVisibility(visible = currentScreen.titleResId != null) {
                        TopAppBar(title = currentScreen.titleResId?.let { stringResource(it) } ?: "")
                    }
                }
            },
            bottomBar = {
                Column {
                    BottomAppBar(
                        currentDestination = currentDestination,
                        navigate = navController::navigate,
                        builder = {
                            popUpTo(Screen.Main.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                }
            },
            contentWindowInsets = insets
        ) { innerPadding ->
            AirTicketsSearchNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Grey7,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
fun BottomAppBar(
    currentDestination: NavDestination?,
    navigate: (String, NavOptions) -> Unit,
    builder: NavOptionsBuilder.() -> Unit
) {
    val items = BottomNavigation.entries
    BottomNavigation {
        items.forEach { item ->
            val isSelected = currentDestination.hasRouteInHierarchy(item.route)
            BottomNavigationItem(
                selected = isSelected,
                onClick = { if (!isSelected) navigate(item.route, navOptions(builder)) },
                item = item
            )
        }
    }
}

@Composable
fun BottomNavigation(content: @Composable RowScope.() -> Unit) {
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth(),
        color = Grey1
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(color = Black),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

/* на material3 не завезли возможность убирать подсветку у выбранного пункта, поэтому кастомный боттом бар,
   можно было использовать material2, но это слишком просто :)
   P.S. иконка самолета странная, пришлось задать явный размер иконок, чтоб отображалось ровно и корректно
   Выглядит всё равно она странно */

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    item: BottomNavigation
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(top = 8.dp, bottom = 8.dp)
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Icon(
                painter = painterResource(id = item.iconResId),
                contentDescription = stringResource(id = item.descriptionResId),
                modifier = Modifier
                    .size(24.dp)
                    .align(alignment = Alignment.Center),
                tint = if (selected) Blue else Grey5
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = stringResource(id = item.labelResId),
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) Blue else Grey5
        )
    }
}

@Composable
fun AirTicketsSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigation.Main.route,
        modifier = modifier
    ) {
        navigation(
            startDestination = Screen.Main.route,
            route = BottomNavigation.Main.route
        ) {
            composable(route = Screen.Main.route) {
                MainScreen(navigate = navController::navigate)
            }

            composable(
                route = Screen.SearchResult.route,
                arguments = Screen.SearchResult.arguments
            ) {
                SearchResultScreen(
                    navigate = navController::navigate,
                    navigateBack = navController::popBackStack
                )
            }

            composable(route = Screen.Empty.route) {
                EmptyScreen(navigate = navController::popBackStack)
            }
        }

        navigation(
            startDestination = Screen.Hotels.route,
            route = BottomNavigation.Hotels.route
        ) {
            composable(route = Screen.Hotels.route) {
                EmptyScreen(navigate = navController::popBackStack)
            }
        }

        navigation(
            startDestination = Screen.Shortly.route,
            route = BottomNavigation.Shortly.route
        ) {
            composable(route = Screen.Shortly.route) {
                EmptyScreen(navigate = navController::popBackStack)
            }
        }

        navigation(
            startDestination = Screen.Subscribes.route,
            route = BottomNavigation.Subscribes.route
        ) {
            composable(route = Screen.Subscribes.route) {
                EmptyScreen(navigate = navController::popBackStack)
            }
        }

        navigation(
            startDestination = Screen.Profile.route,
            route = BottomNavigation.Profile.route
        ) {
            composable(route = Screen.Profile.route) {
                EmptyScreen(navigate = navController::popBackStack)
            }
        }
    }
}

@Composable
fun Button(textResId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(
            text = stringResource(id = textResId),
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
    }
}

@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderResId: Int,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.clickable(onClick = onClick),
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.labelMedium,
        placeholder = {
            Text(text = stringResource(id = placeholderResId))
        },
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Grey4,
            unfocusedContainerColor = Grey4,
            disabledContainerColor = Grey4,
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

@Composable
fun TransparentTextFieldWithIcons(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderResId: Int,
    leadingIconResId: Int?,
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
        leadingIcon = {
            if (leadingIconResId != null) {
                Icon(
                    painter = painterResource(id = leadingIconResId),
                    contentDescription = null,
                    tint = tint
                )
            }
        },
        trailingIcon = {
            if (trailingIconResId != null) {
                IconButton(onClick = onIconClick) {
                    Icon(
                        painter = painterResource(id = trailingIconResId),
                        contentDescription = null,
                        tint = Grey6
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

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}