package com.spaceix

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.spaceix.app.App
import com.spaceix.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SpaceX",
        ) {
            App()
        }
    }
}