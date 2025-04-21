package com.spaceix.designsystem

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import com.spaceix.manager.AppTheme

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformColorScheme(
    private val context: Context,
    private val showDynamicPalette: Boolean,
    private val theme: AppTheme
) {
    private val isDynamicPalette = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    actual val platformDarkScheme: ColorScheme
        get() = if (isDynamicPalette && showDynamicPalette) {
            dynamicDarkColorScheme(context)
        } else {
            darkScheme
        }
    actual val platformLightScheme: ColorScheme
        get() = if (isDynamicPalette && showDynamicPalette) {
            dynamicLightColorScheme(context)
        } else {
            lightScheme
        }
    actual val appTheme: AppTheme
        get() = theme
}