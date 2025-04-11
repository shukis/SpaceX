package com.spaceix.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun SelectableListItem(
    modifier: Modifier = Modifier,
    headlineContent: @Composable () -> Unit,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContentUrl: String? = null,
    trailingIconContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) = ListItem(modifier = modifier.clickable(onClick = onClick),
    headlineContent = headlineContent,
    supportingContent = supportingContent,
    trailingContent = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (trailingIconContent != null) {
                trailingIconContent()
            }
            Icon(Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = null)
        }
    },
    leadingContent = {
        AsyncImage(
            model = leadingContentUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp))
        )
    })