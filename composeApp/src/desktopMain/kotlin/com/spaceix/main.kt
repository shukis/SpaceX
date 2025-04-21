package com.spaceix

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.di.initKoin

fun main() {
    val koinApp = initKoin()
    val koin = koinApp.koin
    val viewModel: ThemeViewModel = koin.get()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SpaceX",
        ) {
            val theme by viewModel.currentTheme.collectAsState()
            App(PlatformColorScheme(theme))
        }
    }
}