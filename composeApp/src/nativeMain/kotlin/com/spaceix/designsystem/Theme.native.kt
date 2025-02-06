package com.spaceix.designsystem

import androidx.compose.material3.ColorScheme

actual class PlatformColorScheme {
    actual val platformDarkScheme: ColorScheme
        get() = darkScheme
    actual val platformLightScheme: ColorScheme
        get() = lightScheme
}