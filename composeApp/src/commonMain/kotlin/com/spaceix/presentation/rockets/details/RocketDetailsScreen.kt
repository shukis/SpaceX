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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.spaceix.designsystem.components.ImageViewer
import com.spaceix.designsystem.components.InfoRow
import com.spaceix.designsystem.components.InfoSection
import com.spaceix.designsystem.components.MasonryImageGrid
import com.spaceix.designsystem.customColorsPalette
import com.spaceix.designsystem.icons.filled.Star
import com.spaceix.designsystem.icons.outlined.Star
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailsScreen(
    rocketId: String,
    navController: NavHostController
) {
    val viewModel = koinViewModel<RocketDetailsViewModel> { parametersOf(rocketId) }
    val rocket = viewModel.rocket.collectAsStateWithLifecycle().value
    val showImageViewer = viewModel.showImageViewer.collectAsStateWithLifecycle().value
    Box(modifier = Modifier.fillMaxSize()) {
        rocket?.let {
            Column {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.onFavouriteClicked() }) {
                            Icon(
                                imageVector = if (rocket.isFavourite) {
                                    Icons.Filled.Star
                                } else {
                                    Icons.Outlined.Star
                                },
                                contentDescription = "Favorite",
                                tint = if (rocket.isFavourite) {
                                    MaterialTheme.customColorsPalette.yellow
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    }
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                text = "${rocket.country.orEmpty()} • ${if (rocket.active == true) "Active" else "Inactive"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    item {
                        InfoSection(title = "Overview") {
                            InfoRow("Type", rocket.type)
                            InfoRow("Company", rocket.company)
                            InfoRow("Cost / Launch", rocket.costPerLaunch?.let { "$$it" })
                            InfoRow("Success Rate", rocket.successRatePct?.let { "$it%" })
                        }
                    }

                    item {
                        InfoSection(title = "Dimensions") {
                            InfoRow("Height", rocket.height?.let { "$it m" })
                            InfoRow("Diameter", rocket.diameter?.let { "$it m" })
                            InfoRow("Mass", rocket.mass?.let { "$it kg" })
                        }
                    }

                    item {
                        InfoSection(title = "Stages") {
                            InfoRow("Stages", rocket.stages.toString())
                            InfoRow("Boosters", rocket.boosters.toString())
                        }
                    }


                    item {
                        InfoSection(title = "Engines") {
                            InfoRow("Count", rocket.engines?.number.toString())
                            InfoRow("Type", rocket.engines?.type)
                        }
                    }

                    item {
                        rocket.payloadWeights.filterNotNull().takeIf { it.isNotEmpty() }
                            ?.let { payloads ->
                                InfoSection(title = "Payloads") {
                                    payloads.forEach {
                                        InfoRow("${it.id}", "${it.kg} kg")
                                    }
                                }
                            }
                    }

                    item {
                        if (rocket.flickrImages.size > 1) {
                            Text("Gallery", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            MasonryImageGrid(rocket.flickrImages) { index ->
                                viewModel.onImageClick(index)
                            }
                        }
                    }
                    item {
                        rocket.description?.let {
                            InfoSection(title = "Description") {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item {
                        rocket.wikipedia?.let { url ->
                            OutlinedButton(onClick = { /* open URL */ }) {
                                Text("Wikipedia")
                            }
                        }
                    }
                }
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
