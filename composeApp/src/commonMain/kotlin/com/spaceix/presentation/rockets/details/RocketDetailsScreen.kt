package com.spaceix.presentation.rockets.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.spaceix.core.navigation.UrlOpener
import com.spaceix.designsystem.components.ImageViewer
import com.spaceix.designsystem.components.InfoRow
import com.spaceix.designsystem.components.InfoSection
import com.spaceix.designsystem.components.MasonryImageGrid
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import com.spaceix.designsystem.util.updateScrollState
import com.spaceix.domain.model.RocketEntity
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spacex.composeapp.generated.resources.Res
import spacex.composeapp.generated.resources.back
import spacex.composeapp.generated.resources.favourite
import spacex.composeapp.generated.resources.gallery
import spacex.composeapp.generated.resources.rocket_details_active
import spacex.composeapp.generated.resources.rocket_details_boosters
import spacex.composeapp.generated.resources.rocket_details_company
import spacex.composeapp.generated.resources.rocket_details_cost
import spacex.composeapp.generated.resources.rocket_details_count
import spacex.composeapp.generated.resources.rocket_details_description
import spacex.composeapp.generated.resources.rocket_details_diameter
import spacex.composeapp.generated.resources.rocket_details_dimensions
import spacex.composeapp.generated.resources.rocket_details_engines
import spacex.composeapp.generated.resources.rocket_details_height
import spacex.composeapp.generated.resources.rocket_details_inactive
import spacex.composeapp.generated.resources.rocket_details_kg
import spacex.composeapp.generated.resources.rocket_details_m
import spacex.composeapp.generated.resources.rocket_details_mass
import spacex.composeapp.generated.resources.rocket_details_overview
import spacex.composeapp.generated.resources.rocket_details_payloads
import spacex.composeapp.generated.resources.rocket_details_stages
import spacex.composeapp.generated.resources.rocket_details_success_rate
import spacex.composeapp.generated.resources.rocket_details_success_rate_percentage
import spacex.composeapp.generated.resources.rocket_details_type
import spacex.composeapp.generated.resources.wikipedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailsScreen(
    rocketId: String,
    navController: NavHostController
) {
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    val viewModel = koinViewModel<RocketDetailsViewModel> { parametersOf(rocketId) }
    val rocket = viewModel.rocket.collectAsStateWithLifecycle().value
    val showImageViewer = viewModel.showImageViewer.collectAsStateWithLifecycle().value
    behavior.updateScrollState(lazyListState)
    Box(modifier = Modifier.fillMaxSize()) {
        rocket?.let {
            Column {
                RocketDetailsTopAppBar(navController, it, viewModel, behavior)
                RocketDetailsContent(it, viewModel, lazyListState)
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
private fun RocketDetailsTopAppBar(
    navController: NavHostController,
    rocket: RocketEntity,
    viewModel: RocketDetailsViewModel,
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
                    imageVector = if (rocket.isFavourite) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.Star
                    },
                    contentDescription = stringResource(Res.string.favourite),
                    tint = if (rocket.isFavourite) {
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
private fun RocketDetailsContent(
    rocket: RocketEntity,
    viewModel: RocketDetailsViewModel,
    lazyListState: LazyListState
) {
    val urlOpener = getKoin().get<UrlOpener>()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState
    ) {
        item {
            rocket.flickrImages.firstOrNull()?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }

        item {
            Column {
                Text(
                    text = rocket.name.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (rocket.active == true) {
                        stringResource(
                            Res.string.rocket_details_active,
                            rocket.country.orEmpty()
                        )
                    } else {
                        stringResource(
                            Res.string.rocket_details_inactive,
                            rocket.country.orEmpty()
                        )
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            InfoSection(title = stringResource(Res.string.rocket_details_overview)) {
                InfoRow(stringResource(Res.string.rocket_details_type), rocket.type)
                InfoRow(
                    stringResource(Res.string.rocket_details_company),
                    rocket.company
                )
                rocket.costPerLaunch?.let {
                    InfoRow(
                        stringResource(Res.string.rocket_details_cost),
                        "$$it"
                    )
                }
                rocket.successRatePct?.let {
                    InfoRow(
                        stringResource(Res.string.rocket_details_success_rate),
                        stringResource(
                            Res.string.rocket_details_success_rate_percentage,
                            it
                        )
                    )
                }
            }
        }

        item {
            InfoSection(title = stringResource(Res.string.rocket_details_dimensions)) {
                rocket.height?.let {
                    InfoRow(
                        stringResource(Res.string.rocket_details_height),
                        stringResource(Res.string.rocket_details_m, it)
                    )
                }
                rocket.diameter?.let {
                    InfoRow(
                        stringResource(Res.string.rocket_details_diameter),
                        stringResource(Res.string.rocket_details_m, it)
                    )
                }
                rocket.mass?.let {
                    InfoRow(
                        stringResource(Res.string.rocket_details_mass),
                        stringResource(Res.string.rocket_details_kg, it)
                    )
                }
            }
        }

        item {
            InfoSection(title = stringResource(Res.string.rocket_details_stages)) {
                InfoRow(
                    stringResource(Res.string.rocket_details_stages),
                    rocket.stages.toString()
                )
                InfoRow(
                    stringResource(Res.string.rocket_details_boosters),
                    rocket.boosters.toString()
                )
            }
        }


        item {
            InfoSection(title = stringResource(Res.string.rocket_details_engines)) {
                InfoRow(
                    stringResource(Res.string.rocket_details_count),
                    rocket.engines?.number.toString()
                )
                InfoRow(
                    stringResource(Res.string.rocket_details_type),
                    rocket.engines?.type
                )
            }
        }

        item {
            rocket.payloadWeights.filterNotNull().takeIf { it.isNotEmpty() }
                ?.let { payloads ->
                    InfoSection(title = stringResource(Res.string.rocket_details_payloads)) {
                        payloads.forEach {
                            InfoRow(
                                it.id,
                                stringResource(
                                    Res.string.rocket_details_kg,
                                    it.kg.toString()
                                )
                            )
                        }
                    }
                }
        }

        item {
            if (rocket.flickrImages.size > 1) {
                Text(
                    stringResource(Res.string.gallery),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                MasonryImageGrid(rocket.flickrImages) { index ->
                    viewModel.onImageClick(index)
                }
            }
        }
        item {
            rocket.description?.let {
                InfoSection(title = stringResource(Res.string.rocket_details_description)) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        item {
            rocket.wikipedia?.let { url ->
                OutlinedButton(onClick = { urlOpener.open(url) }) {
                    Text(stringResource(Res.string.wikipedia))
                }
            }
        }
    }
}
