package com.spaceix.presentation.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.spaceix.core.AnimatedNavHost
import com.spaceix.core.fadeInOutComposable
import com.spaceix.core.navigation.Route
import com.spaceix.core.navigation.observeNavigation
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.designsystem.SpacexTheme
import com.spaceix.presentation.favourites.FavouritesScreen
import com.spaceix.presentation.launches.details.LaunchDetailsScreen
import com.spaceix.presentation.launches.list.LaunchesScreen
import com.spaceix.presentation.rockets.details.RocketDetailsScreen
import com.spaceix.presentation.rockets.list.RocketsScreen
import com.spaceix.presentation.settings.SettingScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun MainRootScreen(platformColorScheme: PlatformColorScheme) {
    SpacexTheme(platformColorScheme) {
        KoinContext {
            val navController = rememberNavController()
            val viewModel = koinViewModel<MainRootViewModel>()
            navController.observeNavigation(viewModel.navigation)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = { BottomNavigation(viewModel, navController) }
            ) { paddingValues ->
                SpaceXNavHost(navController, paddingValues)
            }
        }
    }
}

@Composable
private fun BottomNavigation(viewModel: MainRootViewModel, navController: NavHostController) {
    val items = viewModel.bottomNavigationItems.collectAsStateWithLifecycle().value
    val show = viewModel.showBottomNavigation.collectAsStateWithLifecycle().value
    val backStackEntry = navController.currentBackStackEntryAsState()
    viewModel.onBackStackEntry(backStackEntry.value?.destination?.route)
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        NavigationBar { items.map { item -> BottomNavigationItem(item, viewModel, navController) } }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    item: BottomNavigationItem,
    viewModel: MainRootViewModel,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = { Icon(item.icon, stringResource(item.titleRes)) },
        label = { Text(stringResource(item.titleRes)) },
        selected = item.selected,
        onClick = {
            viewModel.onBottomNavigationItemClicked(
                item.route,
                navController.graph.findStartDestination().id
            )
        }
    )
}

@Composable
private fun SpaceXNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    Column {
        AnimatedNavHost(navController = navController, startDestination = Route.RootGraph) {
            rootNavigation(navController, paddingValues)
            composable<Route.LaunchDetails> {
                val launch = it.toRoute<Route.LaunchDetails>()
                LaunchDetailsScreen(launch.launchId, navController)
            }
            composable<Route.RocketDetails> {
                val rocket = it.toRoute<Route.RocketDetails>()
                RocketDetailsScreen(rocket.id, navController)
            }
            composable<Route.Settings> {
                SettingScreen(navController)
            }
        }
    }
}

private fun NavGraphBuilder.rootNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation<Route.RootGraph>(startDestination = Route.Launches) {
        fadeInOutComposable<Route.Launches> {
            LaunchesScreen(navController, paddingValues)
        }
        fadeInOutComposable<Route.Rockets> {
            RocketsScreen(navController, paddingValues)
        }
        fadeInOutComposable<Route.Favourites> {
            FavouritesScreen(navController, paddingValues)
        }
    }
}