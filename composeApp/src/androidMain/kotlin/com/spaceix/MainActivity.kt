package com.spaceix

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme
import com.spaceix.manager.AppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ThemeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val theme by viewModel.currentTheme.collectAsState()
            val useDynamicColor by viewModel.isDynamicColor.collectAsState()
            App(PlatformColorScheme(LocalContext.current, useDynamicColor, theme))
            UpdateSystemBarColors(this, theme)
        }
    }

    @Composable
    private fun UpdateSystemBarColors(activity: Activity, theme: AppTheme) {
        val isDarkTheme = when (theme) {
            AppTheme.LIGHT -> false
            AppTheme.DARK -> true
            AppTheme.SYSTEM -> isSystemInDarkTheme()
        }
        SideEffect {
            val insetsController =
                WindowInsetsControllerCompat(activity.window, activity.window.decorView)
            insetsController.isAppearanceLightStatusBars = !isDarkTheme
        }
    }
}