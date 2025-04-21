package com.spaceix.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ThemeManager {
    fun getCurrentTheme(scope: CoroutineScope): StateFlow<AppTheme>
    fun isDynamicColor(scope: CoroutineScope): StateFlow<Boolean>

    suspend fun setTheme(theme: AppTheme)
    suspend fun setDynamicColor(enabled: Boolean)
}

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}