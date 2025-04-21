package com.spaceix.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.spaceix.designsystem.Typography
import com.spaceix.manager.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.back
import spacex.composeapp.generated.resources.cancel
import spacex.composeapp.generated.resources.settings_appearance
import spacex.composeapp.generated.resources.settings_change_theme
import spacex.composeapp.generated.resources.settings_dynamic_color
import spacex.composeapp.generated.resources.settings_dynamic_color_icon
import spacex.composeapp.generated.resources.settings_theme
import spacex.composeapp.generated.resources.settings_theme_dialog_title
import spacex.composeapp.generated.resources.settings_theme_icon
import spacex.composeapp.generated.resources.settings_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val useDynamicColor by viewModel.isDynamicColor.collectAsStateWithLifecycle()
    val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val showDynamicColorSwitch by viewModel.showDynamicColorSwitch.collectAsStateWithLifecycle()
    var showThemeDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                }
            )
            LazyColumn {
                item {
                    Text(
                        text = stringResource(Res.string.settings_appearance),
                        style = Typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                item {
                    ListItem(
                        headlineContent = { Text(stringResource(Res.string.settings_theme)) },
                        supportingContent = { Text(currentTheme.name) },
                        leadingContent = {
                            Icon(
                                imageVector = themeToIcon(currentTheme),
                                contentDescription = stringResource(Res.string.settings_theme_icon),
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.clickable(
                            role = Role.Button,
                            onClickLabel = stringResource(Res.string.settings_change_theme),
                            onClick = { showThemeDialog = true }
                        )
                    )
                }

                if (showDynamicColorSwitch) {
                    item {
                        ListItem(
                            headlineContent = { Text(stringResource(Res.string.settings_dynamic_color)) },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Outlined.ColorLens,
                                    contentDescription = stringResource(Res.string.settings_dynamic_color_icon),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingContent = {
                                Switch(
                                    checked = useDynamicColor,
                                    onCheckedChange = { enabled ->
                                        viewModel.onDynamicColorSwitchChanged(enabled)
                                    }
                                )
                            },
                            modifier = Modifier.clickable(
                                role = Role.Switch,
                                onClick = { viewModel.onDynamicColorSwitchChanged(!useDynamicColor) }
                            )
                        )
                    }

                    item { Spacer(modifier = Modifier.padding(vertical = 8.dp)) }
                }
            }
        }
        if (showThemeDialog) {
            ThemeSelectionDialog(
                currentSelection = currentTheme,
                onDismiss = { showThemeDialog = false },
                onSelectTheme = { theme ->
                    viewModel.onThemeChanged(theme)
                    showThemeDialog = false
                }
            )
        }
    }
}

fun themeToIcon(theme: AppTheme): ImageVector {
    return when (theme) {
        AppTheme.LIGHT -> Icons.Outlined.LightMode
        AppTheme.DARK -> Icons.Outlined.DarkMode
        AppTheme.SYSTEM -> Icons.Outlined.BrightnessMedium
    }
}

@Composable
fun ThemeSelectionDialog(
    currentSelection: AppTheme,
    onDismiss: () -> Unit,
    onSelectTheme: (AppTheme) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.settings_theme_dialog_title)) },
        icon = { Icon(Icons.Outlined.BrightnessMedium, contentDescription = null) },
        text = {
            Column {
                AppTheme.entries.forEach { theme ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (theme == currentSelection),
                                onClick = { onSelectTheme(theme) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (theme == currentSelection),
                            onClick = null
                        )
                        Text(
                            text = theme.name,
                            style = Typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.cancel))
            }
        }
    )
}