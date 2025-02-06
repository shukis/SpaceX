package com.spaceix.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.designsystem.SpacexTheme
import com.spaceix.presentation.launches.AllLaunchesScreen
import com.spaceix.presentation.launches.AllLaunchesViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(platformColorScheme: PlatformColorScheme) {
    SpacexTheme(platformColorScheme) {
        KoinContext {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                val viewModel = koinViewModel<AllLaunchesViewModel>()
                Column(modifier = Modifier.statusBarsPadding().fillMaxSize()) {
                    AllLaunchesScreen(viewModel) {}
                }
            }
        }
    }
}