package com.spaceix.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String?,
    onSettingsClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title.orEmpty()) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = onSettingsClicked) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}