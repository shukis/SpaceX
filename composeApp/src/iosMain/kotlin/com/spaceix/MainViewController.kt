package com.spaceix

import androidx.compose.ui.window.ComposeUIViewController
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.di.initKoin
import com.spaceix.manager.AppTheme

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App(PlatformColorScheme(AppTheme.SYSTEM)) } // mock