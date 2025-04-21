package com.spaceix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.manager.AppTheme
import com.spaceix.manager.ThemeManager
import kotlinx.coroutines.flow.StateFlow

actual class ThemeViewModel(themeManager: ThemeManager) : ViewModel() {
    actual val currentTheme: StateFlow<AppTheme> = themeManager.getCurrentTheme(viewModelScope)
    actual val isDynamicColor: StateFlow<Boolean> = themeManager.isDynamicColor(viewModelScope)
}