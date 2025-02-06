package com.spaceix.presentation.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AllLaunchesScreen(
    viewModel: AllLaunchesViewModel = koinViewModel(),
    onRocketClicked: (String) -> Unit,
) {
    val launches = viewModel.launches.collectAsStateWithLifecycle().value
    val showLoader = viewModel.showLoader.collectAsStateWithLifecycle().value
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            println("Clicked")
            viewModel.onBtnClicked()
        }) {
            Text("Click me")
        }
        if (showLoader) {
            CircularProgressIndicator(
                modifier = Modifier.width(24.dp),
//                color = MaterialTheme.colorScheme.secondary,
//                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            Text(text = launches?.firstOrNull()?.missionName ?: "oppa")
        }
    }
}