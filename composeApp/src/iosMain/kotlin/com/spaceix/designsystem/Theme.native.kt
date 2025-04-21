package com.spaceix.designsystem

import androidx.compose.material3.ColorScheme
import com.spaceix.manager.AppTheme

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformColorScheme(private val theme: AppTheme) {
    actual val platformDarkScheme: ColorScheme
        get() = darkScheme
    actual val platformLightScheme: ColorScheme
        get() = lightScheme
    actual val appTheme: AppTheme
        get() = theme
}