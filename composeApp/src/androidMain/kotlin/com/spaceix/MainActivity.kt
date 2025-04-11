package com.spaceix

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            App(PlatformColorScheme(LocalContext.current, true))
            UpdateSystemBarColors(this)
        }
    }

    @Composable
    private fun UpdateSystemBarColors(activity: Activity) {
        val isDarkTheme = isSystemInDarkTheme()
        SideEffect {
            val insetsController =
                WindowInsetsControllerCompat(activity.window, activity.window.decorView)
            insetsController.isAppearanceLightStatusBars = !isDarkTheme
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(PlatformColorScheme(LocalContext.current, true))
}