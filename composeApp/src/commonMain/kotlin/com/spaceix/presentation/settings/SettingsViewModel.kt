package com.spaceix.presentation.settings

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.data.datastore.SpaceXDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

class SettingsViewModel(
    private val dataStore: SpaceXDataStore,
) : ViewModel() {

    private val theme = stringPreferencesKey("theme")

    val currentTheme = dataStore.prefs.data.map { it[theme] ?: "not set" }

    init {
        viewModelScope.launch {
            delay(2000)
            dataStore.prefs
                .edit {
                    val theme = theme
                    it[theme] = "system ${Random.nextInt()}"
                }
        }
    }
}