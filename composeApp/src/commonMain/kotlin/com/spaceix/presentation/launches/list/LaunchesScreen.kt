package com.spaceix.presentation.launches.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.spaceix.core.navigation.observeNavigation
import com.spaceix.designsystem.Typography
import com.spaceix.designsystem.components.RootTopAppBar
import com.spaceix.designsystem.components.SelectableListItem
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import com.spaceix.designsystem.util.updateScrollState
import com.spaceix.domain.model.LaunchEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.bottom_nav_launches
import spacex.composeapp.generated.resources.launch_failure
import spacex.composeapp.generated.resources.launch_success


@Composable
fun LaunchesScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel = koinViewModel<LaunchesViewModel>()
    navController.observeNavigation(viewModel.navigation)
    val showLoader = viewModel.showLoader.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) { viewModel.onViewResumed() }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if (showLoader) {
            CircularProgressIndicator(modifier = Modifier.width(48.dp))
        } else {
            LaunchesColumn(viewModel, paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LaunchesColumn(
    viewModel: LaunchesViewModel,
    paddingValues: PaddingValues
) {
    val launches = viewModel.launches.collectAsStateWithLifecycle().value.orEmpty()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    behavior.updateScrollState(lazyListState)
    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
        stickyHeader {
            RootTopAppBar(behavior, stringResource(Res.string.bottom_nav_launches)) {
                viewModel.onSettingsClicked()
            }
        }
        items(launches, key = { it.id }) { launch ->
            SelectableListItem(
                headlineContent = { Text(text = launch.name) },
                supportingContent = {
                    SupportingContent(
                        launch.launchSuccess,
                        launch.launchDateUtc
                    )
                },
                leadingContentUrl = launch.links.missionPatchSmall,
                trailingIconContent = {
                    IconButton(onClick = { viewModel.onFavouriteClicked(launch) }) {
                        Icon(
                            imageVector = if (launch.isFavourite) {
                                Icons.Filled.Star
                            } else {
                                Icons.Outlined.Star
                            },
                            contentDescription = null,
                            tint = if (launch.isFavourite) {
                                MaterialTheme.customColorsPalette.yellow
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }) { viewModel.onLaunchClicked(launch.id) }
        }
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
    }
}

@Composable
private fun SupportingContent(success: Boolean, date: Instant) {
    val color = if (success) {
        MaterialTheme.customColorsPalette.success
    } else {
        MaterialTheme.colorScheme.error
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.background(
                color = color, shape = RoundedCornerShape(4.dp)
            ).padding(horizontal = 4.dp, vertical = 2.dp),
            text = stringResource(
                if (success) {
                    Res.string.launch_success
                } else {
                    Res.string.launch_failure
                }
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            style = Typography.labelSmall
        )
        Spacer(Modifier.width(12.dp))
        Text(text = format(date))
    }
}

@Composable
private fun format(instant: Instant): String = remember(instant) {
    val customFormat = DateTimeComponents.Format {
        dayOfMonth();char('.');monthNumber();char('.');year()
    }
    instant.format(customFormat)
}