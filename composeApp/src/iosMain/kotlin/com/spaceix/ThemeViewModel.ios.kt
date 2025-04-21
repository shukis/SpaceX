package com.spaceix

import com.spaceix.manager.AppTheme
import com.spaceix.manager.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

actual class ThemeViewModel(themeManager: ThemeManager, scope: CoroutineScope) {
    actual val currentTheme: StateFlow<AppTheme> = themeManager.getCurrentTheme(scope)
    actual val isDynamicColor: StateFlow<Boolean> = themeManager.isDynamicColor(scope)
}