package com.spaceix.presentation.rockets.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.spaceix.core.navigation.observeNavigation
import com.spaceix.designsystem.components.RootTopAppBar
import com.spaceix.designsystem.components.SelectableListItem
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import com.spaceix.designsystem.util.updateScrollState
import com.spaceix.domain.model.RocketEntity
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.bottom_nav_rockets

@Composable
fun RocketsScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel = koinViewModel<RocketsViewModel>()
    navController.observeNavigation(viewModel.navigation)
    val showLoader = viewModel.showLoader.collectAsStateWithLifecycle().value
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        if (showLoader) {
            CircularProgressIndicator(modifier = Modifier.width(48.dp))
        } else {
            RocketsColumn(viewModel, paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun RocketsColumn(
    viewModel: RocketsViewModel,
    paddingValues: PaddingValues
) {
    val rockets = viewModel.rockets.collectAsStateWithLifecycle().value.orEmpty()
    val lazyListState = rememberLazyListState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    behavior.updateScrollState(lazyListState)
    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
        stickyHeader {
            RootTopAppBar(behavior, stringResource(Res.string.bottom_nav_rockets)) {
                viewModel.onSettingsClicked()
            }
        }
        items(rockets) { rocket ->
            SelectableListItem(
                headlineContent = { Text(text = rocket.name.orEmpty()) },
                leadingContentUrl = rocket.flickrImages.first(),
                trailingIconContent = {
                    IconButton(onClick = { viewModel.onFavouriteClicked(rocket) }) {
                        Icon(
                            imageVector = if (rocket.isFavourite) {
                                Icons.Filled.Star
                            } else {
                                Icons.Outlined.Star
                            },
                            contentDescription = null,
                            tint = if (rocket.isFavourite) {
                                MaterialTheme.customColorsPalette.yellow
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            ) { viewModel.onRocketClicked(rocket.id) }
        }
        item {
            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
    }
}
