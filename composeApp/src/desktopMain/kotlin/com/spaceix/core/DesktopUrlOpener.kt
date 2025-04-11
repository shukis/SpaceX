package com.spaceix.core

import com.spaceix.core.navigation.UrlOpener
import java.awt.Desktop
import java.net.URI

class DesktopUrlOpener : UrlOpener {
    override fun open(url: String) {
        Desktop.getDesktop().browse(URI(url))
    }
}