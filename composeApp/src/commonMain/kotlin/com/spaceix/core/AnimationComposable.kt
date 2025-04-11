package com.spaceix.core

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

const val ANIM_DURATION = 500

inline fun <reified T : Any> NavGraphBuilder.fadeInOutComposable(
    noinline enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        fadeIn(animationSpec = tween(ANIM_DURATION))
    },
    noinline exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut(animationSpec = tween(ANIM_DURATION))
    },
    noinline popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        fadeIn(animationSpec = tween(ANIM_DURATION))
    },
    noinline popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        fadeOut(animationSpec = tween(ANIM_DURATION))
    },
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: Any,
    builder: NavGraphBuilder.() -> Unit
) = NavHost(
    modifier = Modifier.fillMaxSize(),
    navController = navController,
    startDestination = startDestination,
    enterTransition = {
        slideInHorizontally(
            initialOffsetX = { it }, animationSpec = tween(ANIM_DURATION)
        ) + fadeIn(animationSpec = tween(ANIM_DURATION))
    },
    exitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it }, animationSpec = tween(ANIM_DURATION)
        ) + fadeOut(animationSpec = tween(ANIM_DURATION))
    },
    popEnterTransition = {
        slideInHorizontally(
            initialOffsetX = { -it }, animationSpec = tween(ANIM_DURATION)
        ) + fadeIn(animationSpec = tween(ANIM_DURATION))
    },
    popExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { it }, animationSpec = tween(ANIM_DURATION)
        ) + fadeOut(animationSpec = tween(ANIM_DURATION))
    },
    builder = builder
)