package com.spaceix.presentation.favourites

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.spaceix.core.format
import com.spaceix.core.navigation.observeNavigation
import com.spaceix.designsystem.Typography
import com.spaceix.designsystem.components.RootTopAppBar
import com.spaceix.designsystem.components.SelectableListItem
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import com.spaceix.designsystem.util.updateScrollState
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.model.RocketEntity
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.bottom_nav_favourites
import spacex.composeapp.generated.resources.bottom_nav_launches
import spacex.composeapp.generated.resources.bottom_nav_rockets
import spacex.composeapp.generated.resources.favourites_empty_list
import spacex.composeapp.generated.resources.launch_failure
import spacex.composeapp.generated.resources.launch_success


@Composable
fun FavouritesScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val viewModel = koinViewModel<FavouritesViewModel>()
    navController.observeNavigation(viewModel.navigation)
    FavouritesColumn(viewModel, paddingValues)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouritesColumn(viewModel: FavouritesViewModel, paddingValues: PaddingValues) {
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Column {
        RootTopAppBar(behavior, stringResource(Res.string.bottom_nav_favourites)) {
            viewModel.onSettingsClicked()
        }
        FavouritesContent(viewModel, behavior, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouritesContent(
    viewModel: FavouritesViewModel,
    behavior: TopAppBarScrollBehavior,
    paddingValues: PaddingValues
) {
    val lazyListState = rememberLazyListState()
    val launches = viewModel.launches.collectAsStateWithLifecycle().value.orEmpty()
    val rockets = viewModel.rockets.collectAsStateWithLifecycle().value.orEmpty()
    val showLoader = viewModel.showLoader.collectAsStateWithLifecycle().value
    val showEmptyText = viewModel.showEmptyListText.collectAsStateWithLifecycle(false).value
    behavior.updateScrollState(lazyListState)
    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
        launchItems(launches, viewModel, lazyListState)
        rocketItems(rockets, viewModel, lazyListState)
        loaderOrEmptyListItem(showLoader, showEmptyText)
        footerItem(paddingValues)
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.launchItems(
    launches: List<LaunchEntity>,
    viewModel: FavouritesViewModel,
    lazyListState: LazyListState
) {
    if (launches.isNotEmpty()) {
        stickyHeader {
            StickyHeader(
                title = stringResource(Res.string.bottom_nav_launches),
                lazyListState
            )
        }
    }
    items(launches, key = { it.id }) { launch -> LaunchItem(launch, viewModel) }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.rocketItems(
    rockets: List<RocketEntity>,
    viewModel: FavouritesViewModel,
    lazyListState: LazyListState
) {
    if (rockets.isNotEmpty()) {
        stickyHeader {
            StickyHeader(
                title = stringResource(Res.string.bottom_nav_rockets),
                lazyListState
            )
        }
    }
    items(rockets, key = { it.id }) { rocket -> RocketItem(rocket, viewModel) }
}

private fun LazyListScope.loaderOrEmptyListItem(showLoader: Boolean, showEmptyList: Boolean) {
    item {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            when {
                showLoader -> CircularProgressIndicator(
                    modifier = Modifier.width(24.dp).height(24.dp)
                )

                showEmptyList -> Text(
                    text = stringResource(Res.string.favourites_empty_list),
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 96.dp),
                    style = Typography.titleMedium
                )
            }
        }
    }
}

private fun LazyListScope.footerItem(paddingValues: PaddingValues) {
    item {
        Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
    }
}

@Composable
fun LazyItemScope.StickyHeader(title: String, lazyListState: LazyListState) {
    var isStuck by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        if (isStuck) {
            MaterialTheme.colorScheme.surfaceContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "StickyHeaderColor",
        animationSpec = tween(durationMillis = 250)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateItem()
            .background(backgroundColor)
            .onGloballyPositioned { coords ->
                isStuck = coords.positionInParent().y == 0f &&
                        lazyListState.firstVisibleItemScrollOffset > 0
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun LazyItemScope.LaunchItem(launch: LaunchEntity, viewModel: FavouritesViewModel) {
    SelectableListItem(
        modifier = Modifier.animateItem(),
        headlineContent = { Text(text = launch.name) },
        supportingContent = {
            SupportingContent(
                launch.launchSuccess,
                launch.launchDateUtc
            )
        },
        leadingContentUrl = launch.links.missionPatchSmall,
        trailingIconContent = {
            IconButton(onClick = { viewModel.onFavouriteLaunchClicked(launch) }) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.customColorsPalette.yellow
                )
            }
        }) { viewModel.onLaunchClicked(launch.id) }
}

@Composable
private fun LazyItemScope.RocketItem(rocket: RocketEntity, viewModel: FavouritesViewModel) {
    SelectableListItem(
        modifier = Modifier.animateItem(),
        headlineContent = { Text(text = rocket.name.orEmpty()) },
        leadingContentUrl = rocket.flickrImages.first(),
        trailingIconContent = {
            IconButton(onClick = { viewModel.onFavouriteRocketClicked(rocket) }) {
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