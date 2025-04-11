package com.spaceix.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object RootGraph : Route

    @Serializable
    data object Launches : Route

    @Serializable
    data class LaunchDetails(val launchId: String) : Route

    @Serializable
    data object Rockets : Route

    @Serializable
    data class RocketDetails(val id: String) : Route

    @Serializable
    data object Favourites : Route

    @Serializable
    data object Settings : Route
}

data class Navigation(
    val route: Route,
    val navigationOptions: (NavOptionsBuilder.() -> Unit)? = null
)

@Composable
fun NavHostController.observeNavigation(flow: SharedFlow<Navigation>) {
    LaunchedEffect(Unit) {
        flow.collect { navigation ->
            navigate(
                navigation.route,
                navigation.navigationOptions?.let { builder -> navOptions(builder) }
            )
        }
    }
}