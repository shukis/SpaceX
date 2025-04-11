package com.spaceix.app

import androidx.compose.runtime.Composable
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.presentation.root.MainRootScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(platformColorScheme: PlatformColorScheme) {
    MainRootScreen(platformColorScheme)
}