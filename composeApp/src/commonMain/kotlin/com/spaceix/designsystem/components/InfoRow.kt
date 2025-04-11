package com.spaceix.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.spaceix.designsystem.Typography

@Composable
fun InfoRow(label: String, value: String?) {
    if (value == null) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = Typography.bodyMedium)
        Text(value, style = Typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}