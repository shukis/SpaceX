package com.spaceix

import com.spaceix.manager.AppTheme
import kotlinx.coroutines.flow.StateFlow

expect class ThemeViewModel {
    val currentTheme: StateFlow<AppTheme>
    val isDynamicColor: StateFlow<Boolean>
}