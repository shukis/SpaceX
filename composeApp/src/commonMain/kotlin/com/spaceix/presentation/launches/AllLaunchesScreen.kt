package com.spaceix.presentation.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AllLaunchesScreen(
    viewModel: AllLaunchesViewModel = koinViewModel(),
    onRocketClicked: (String) -> Unit,
) {
    Column {
        Button(onClick = {
            println("Clicked")
            viewModel.onBtnClicked()
        }) {
            Text("Click me")
        }
    }

}