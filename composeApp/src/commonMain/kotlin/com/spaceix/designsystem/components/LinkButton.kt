package com.spaceix.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.spaceix.core.navigation.UrlOpener
import com.spaceix.designsystem.Typography

@Composable
fun LinkButton(url: String, urlOpener: UrlOpener) {
    Text(
        text = url,
        style = Typography.bodyMedium.copy(
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable {
                urlOpener.open(url)
            }
    )
}
