package com.spaceix.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.spaceix.core.navigation.observeNavigation
import com.spaceix.presentation.rockets.list.RocketsViewModel
import okio.Path.Companion.toPath
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingScreen(navController: NavHostController) {
    val viewModel = koinViewModel<SettingsViewModel>()
//    navController.observeNavigation(viewModel.navigation)
    val currentTheme = viewModel.currentTheme.collectAsStateWithLifecycle("not set").value

    Column {
        Spacer(modifier = Modifier.height(120.dp))
        Text(currentTheme)
    }
}