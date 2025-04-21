package com.spaceix.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

@Composable
fun format(instant: Instant): String = remember(instant) {
    val customFormat = DateTimeComponents.Format {
        dayOfMonth(); char('.'); monthNumber(); char('.'); year()
    }
    instant.format(customFormat)
}