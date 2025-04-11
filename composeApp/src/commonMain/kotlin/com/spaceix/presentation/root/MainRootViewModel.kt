package com.spaceix.presentation.root

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.core.navigation.Navigation
import com.spaceix.core.navigation.Route
import com.spaceix.designsystem.icons.filled.Rocket
import com.spaceix.designsystem.icons.filled.RocketLaunch
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Rocket
import com.spaceix.designsystem.icons.outlined.RocketLaunch
import com.spaceix.designsystem.icons.outlined.Star
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.bottom_nav_favourites
import spacex.composeapp.generated.resources.bottom_nav_launches
import spacex.composeapp.generated.resources.bottom_nav_rockets

class MainRootViewModel : ViewModel() {

    private val _bottomNavigationItems = MutableStateFlow<List<BottomNavigationItem>>(emptyList())
    val bottomNavigationItems = _bottomNavigationItems.asStateFlow()

    private val _showBottomNavigation = MutableStateFlow(true)
    val showBottomNavigation = _showBottomNavigation.asStateFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    init {
        viewModelScope.launch { setupBottomNavigationItems(Route.Launches) }
    }

    private suspend fun setupBottomNavigationItems(selectedRoute: Route) {
        if (_bottomNavigationItems.value.find { it.selected }?.route != selectedRoute) {
            listOf(
                getLaunchesBottomNavigationItem(selectedRoute),
                getRocketsBottomNavigationItem(selectedRoute),
                getFavouritesBottomNavigationItem(selectedRoute),
            ).let { _bottomNavigationItems.emit(it) }
        }
    }

    private fun getLaunchesBottomNavigationItem(selectedRoute: Route): BottomNavigationItem {
        val isSelected = selectedRoute == Route.Launches
        val icon = if (isSelected) {
            Icons.Filled.RocketLaunch
        } else {
            Icons.Outlined.RocketLaunch
        }
        return BottomNavigationItem(
            titleRes = Res.string.bottom_nav_launches,
            icon = icon,
            selected = isSelected,
            Route.Launches
        )
    }

    private fun getRocketsBottomNavigationItem(selectedRoute: Route): BottomNavigationItem {
        val isSelected = selectedRoute == Route.Rockets
        val icon = if (isSelected) {
            Icons.Filled.Rocket
        } else {
            Icons.Outlined.Rocket
        }
        return BottomNavigationItem(
            titleRes = Res.string.bottom_nav_rockets,
            icon = icon,
            selected = isSelected,
            Route.Rockets
        )
    }

    private fun getFavouritesBottomNavigationItem(selectedRoute: Route): BottomNavigationItem {
        val isSelected = selectedRoute == Route.Favourites
        val icon = if (isSelected) {
            Icons.Filled.Star
        } else {
            Icons.Outlined.Star
        }
        return BottomNavigationItem(
            titleRes = Res.string.bottom_nav_favourites,
            icon = icon,
            selected = isSelected,
            Route.Favourites
        )
    }

    fun onBackStackEntry(route: String?) {
        val isRoot = route in listOf(
            Route.Launches,
            Route.Rockets,
            Route.Favourites
        ).map { it::class.qualifiedName }
        viewModelScope.launch {
            _showBottomNavigation.emit(isRoot)
            _bottomNavigationItems.value.firstOrNull { it.route::class.qualifiedName == route }?.route?.let {
                setupBottomNavigationItems(it)
            }
        }
    }

    fun onBottomNavigationItemClicked(route: Route, startDestinationId: Int) {
        if (_bottomNavigationItems.value.find { it.selected }?.route != route) {
            viewModelScope.launch {
                _navigation.emit(Navigation(route) {
                    popUpTo(startDestinationId)
                    launchSingleTop = true
                })
            }
        }
    }

    fun onLaunchClicked(launchId: String) {
        viewModelScope.launch {
            _navigation.emit(Navigation(Route.LaunchDetails(launchId)) {

            })
        }
    }

    fun onRocketClicked(rocketId: String) {

    }
}

data class BottomNavigationItem(
    val titleRes: StringResource,
    val icon: ImageVector,
    val selected: Boolean,
    val route: Route
)

