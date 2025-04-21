package com.spaceix.presentation.launches.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.spaceix.core.format
import com.spaceix.core.navigation.UrlOpener
import com.spaceix.designsystem.Typography
import com.spaceix.designsystem.components.ImageViewer
import com.spaceix.designsystem.components.InfoRow
import com.spaceix.designsystem.components.InfoSection
import com.spaceix.designsystem.components.MasonryImageGrid
import com.spaceix.designsystem.components.Shimmer
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import com.spaceix.designsystem.util.updateScrollState
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.model.LaunchEntity
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.article
import spacex.composeapp.generated.resources.back
import spacex.composeapp.generated.resources.details
import spacex.composeapp.generated.resources.favourite
import spacex.composeapp.generated.resources.gallery
import spacex.composeapp.generated.resources.launch_details_crew
import spacex.composeapp.generated.resources.launch_details_date
import spacex.composeapp.generated.resources.launch_details_failure_reason
import spacex.composeapp.generated.resources.launch_details_flight_number
import spacex.composeapp.generated.resources.launch_details_info
import spacex.composeapp.generated.resources.launch_details_status
import spacex.composeapp.generated.resources.launch_failure
import spacex.composeapp.generated.resources.launch_success
import spacex.composeapp.generated.resources.reddit
import spacex.composeapp.generated.resources.status
import spacex.composeapp.generated.resources.webcast
import spacex.composeapp.generated.resources.wikipedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailsScreen(
    launchId: String,
    navController: NavHostController
) {
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    val viewModel = koinViewModel<LaunchDetailsViewModel> { parametersOf(launchId) }
    val launch = viewModel.launch.collectAsStateWithLifecycle().value
    val showImageViewer = viewModel.showImageViewer.collectAsStateWithLifecycle().value
    behavior.updateScrollState(lazyListState)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            launch?.let {
                LaunchDetailsTopAppBar(navController, it, viewModel, behavior)
                LaunchDetailsContent(it, viewModel, lazyListState)
            }
        }
        if (showImageViewer != null) {
            ImageViewer(
                images = showImageViewer.first,
                initialIndex = showImageViewer.second,
                onDismiss = { viewModel.onImageViewerDismissed() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LaunchDetailsTopAppBar(
    navController: NavHostController,
    launch: LaunchEntity,
    viewModel: LaunchDetailsViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = { viewModel.onFavouriteClicked() }) {
                Icon(
                    imageVector = if (launch.isFavourite) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.Star
                    },
                    contentDescription = stringResource(Res.string.favourite),
                    tint = if (launch.isFavourite) {
                        MaterialTheme.customColorsPalette.yellow
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    )
}

@Composable
private fun LaunchDetailsContent(
    launch: LaunchEntity,
    viewModel: LaunchDetailsViewModel,
    lazyListState: LazyListState
) {
    val crew = viewModel.crew.collectAsStateWithLifecycle().value

    val urlOpener = getKoin().get<UrlOpener>()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState
    ) {
        item {
            Row {
                AsyncImage(
                    model = launch.links.missionPatchSmall,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
        item {
            Text(
                text = launch.name,
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            InfoSection(title = stringResource(Res.string.launch_details_info)) {
                InfoRow(
                    label = stringResource(Res.string.launch_details_flight_number),
                    value = "#${launch.flightNumber}"
                )
                InfoRow(
                    label = stringResource(Res.string.launch_details_date),
                    value = format(launch.launchDateUtc)
                )
                InfoRow(
                    label = stringResource(Res.string.status),
                    value = stringResource(
                        if (launch.launchSuccess) {
                            Res.string.launch_success
                        } else {
                            Res.string.launch_failure
                        }
                    )
                )
                launch.launchFailureDetails?.reason?.let {
                    InfoRow(
                        label = stringResource(Res.string.launch_details_failure_reason),
                        value = it
                    )
                }
            }
        }
        item {
            if (launch.links.flickrImages?.isNotEmpty() == true) {
                Text(
                    stringResource(Res.string.gallery),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                MasonryImageGrid(launch.links.flickrImages) { index ->
                    viewModel.onImageClick(index)
                }
            }
        }
        launch.details?.let {
            item {
                InfoSection(title = stringResource(Res.string.details)) {
                    Text(text = it, style = Typography.bodyMedium)
                }
            }
        }
        if (launch.crew.isNotEmpty()) {
            item {
                Text(
                    stringResource(Res.string.launch_details_crew),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                crew?.let { list ->
                    list.forEach { crewMember ->
                        CrewMemberBox(crewMember)
                        Spacer(Modifier.height(8.dp))
                    }
                } ?: repeat(launch.crew.size) {
                    Shimmer(height = 148)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                launch.links.wikipedia?.let {
                    OutlinedButton(onClick = { urlOpener.open(it) }) {
                        Text(stringResource(Res.string.wikipedia))
                    }
                }
                launch.links.reddit?.let {
                    OutlinedButton(onClick = { urlOpener.open(it) }) {
                        Text(stringResource(Res.string.reddit))
                    }
                }
                launch.links.article?.let {
                    OutlinedButton(onClick = { urlOpener.open(it) }) {
                        Text(stringResource(Res.string.article))
                    }
                }
                launch.links.webcast?.let {
                    OutlinedButton(onClick = { urlOpener.open(it) }) {
                        Text(stringResource(Res.string.webcast))
                    }
                }
            }
        }
    }
}

@Composable
fun CrewMemberBox(crewMember: CrewMemberEntity) {
    val urlOpener = getKoin().get<UrlOpener>()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = crewMember.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = crewMember.name,
                    style = Typography.titleMedium
                )

                crewMember.agency?.let {
                    Text(
                        text = it,
                        style = Typography.bodyMedium
                    )
                }

                crewMember.status?.let {
                    Text(
                        text = stringResource(Res.string.launch_details_status, it),
                        style = Typography.bodySmall
                    )
                }

                crewMember.wikipedia?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { urlOpener.open(it) }) {
                        Text(stringResource(Res.string.wikipedia))
                    }
                }
            }
        }
    }
}