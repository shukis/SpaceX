package com.spaceix.core

import com.spaceix.core.navigation.UrlOpener
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IosUrlOpener : UrlOpener {
    override fun open(url: String) {
        val nsUrl = NSURL(string = url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}