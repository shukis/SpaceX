package com.spaceix.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.core.Platform
import com.spaceix.core.isAndroid12OrAbove
import com.spaceix.manager.AppTheme
import com.spaceix.manager.ThemeManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val themeManager: ThemeManager
) : ViewModel() {

    val currentTheme = themeManager.getCurrentTheme(viewModelScope)
    val isDynamicColor = themeManager.isDynamicColor(viewModelScope)

    private val _showDynamicColorSwitch = MutableStateFlow(false)
    val showDynamicColorSwitch = _showDynamicColorSwitch.asStateFlow()

    init {
        viewModelScope.launch {
            _showDynamicColorSwitch.emit(Platform.isAndroid && isAndroid12OrAbove())
        }
    }

    fun onDynamicColorSwitchChanged(enabled: Boolean) {
        viewModelScope.launch {
            themeManager.setDynamicColor(enabled)
        }
    }

    fun onThemeChanged(theme: AppTheme) {
        viewModelScope.launch {
            themeManager.setTheme(theme)
        }
    }
}