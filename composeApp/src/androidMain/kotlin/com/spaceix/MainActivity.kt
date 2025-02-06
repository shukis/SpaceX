package com.spaceix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.spaceix.app.App
import com.spaceix.designsystem.PlatformColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App(PlatformColorScheme(LocalContext.current)) }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(PlatformColorScheme(LocalContext.current))
}