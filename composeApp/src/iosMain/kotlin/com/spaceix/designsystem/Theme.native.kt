package com.spaceix.designsystem

import androidx.compose.material3.ColorScheme

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformColorScheme {
    actual val platformDarkScheme: ColorScheme
        get() = darkScheme
    actual val platformLightScheme: ColorScheme
        get() = lightScheme
}