package com.spaceix.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MasonryImageGrid(
    images: List<String?>,
    onImageClick: (Int) -> Unit = {}
) {
    val (left, right) = images.filterNotNull().withIndex()
        .partition { it.index % 2 == 0 }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            left.forEach { image ->
                MasonryImage(image, onImageClick)
            }
        }
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            right.forEach { image ->
                MasonryImage(image, onImageClick)
            }
        }
    }
}

@Composable
fun MasonryImage(image: IndexedValue<String>, onImageClick: (Int) -> Unit = {}) {
    val randomHeight = remember { (120..220).random().dp }
    AsyncImage(
        model = image.value,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(randomHeight)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onImageClick(image.index) }
    )
}