package com.spaceix.data.manager

import androidx.datastore.preferences.core.edit
import com.spaceix.data.datastore.SpaceXDataStore
import com.spaceix.data.datastore.dynamicColorKey
import com.spaceix.data.datastore.themeKey
import com.spaceix.manager.AppTheme
import com.spaceix.manager.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ThemeManagerImpl(private val dataStore: SpaceXDataStore) : ThemeManager {

    override fun getCurrentTheme(scope: CoroutineScope): StateFlow<AppTheme> {
        return dataStore.prefs.data
            .map { preferences ->
                when (preferences[themeKey]) {
                    AppTheme.LIGHT.name -> AppTheme.LIGHT
                    AppTheme.DARK.name -> AppTheme.DARK
                    else -> AppTheme.SYSTEM
                }
            }.stateIn(scope, SharingStarted.Eagerly, AppTheme.SYSTEM)
    }

    override fun isDynamicColor(scope: CoroutineScope): StateFlow<Boolean> {
        return dataStore.prefs.data.map { preferences ->
            preferences[dynamicColorKey] ?: true
        }.stateIn(scope, SharingStarted.Eagerly, true)
    }

    override suspend fun setTheme(theme: AppTheme) {
        dataStore.prefs.edit { preferences -> preferences[themeKey] = theme.name }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.prefs.edit { prefs -> prefs[dynamicColorKey] = enabled }
    }
}