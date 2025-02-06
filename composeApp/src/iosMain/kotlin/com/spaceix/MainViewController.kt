package com.spaceix

import androidx.compose.ui.window.ComposeUIViewController
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App(PlatformColorScheme()) }