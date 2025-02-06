package com.spaceix.designsystem

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformColorScheme(
    private val context: Context
) {
    private val isDynamicPalette = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    actual val platformDarkScheme: ColorScheme
        get() = if (isDynamicPalette) {
            darkScheme
//            dynamicDarkColorScheme(context)
        } else {
            darkScheme
        }
    actual val platformLightScheme: ColorScheme
        get() = if (isDynamicPalette) {
            lightScheme
//            dynamicLightColorScheme(context)
        } else {
            lightScheme
        }
}