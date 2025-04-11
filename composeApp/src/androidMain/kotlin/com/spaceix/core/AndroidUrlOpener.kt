package com.spaceix.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import com.spaceix.core.navigation.UrlOpener

class AndroidUrlOpener(private val context: Context) : UrlOpener {
    override fun open(url: String) {
        val uri = Uri.parse(url)

        if (isChromeTabSupported(context, uri)) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            customTabsIntent.launchUrl(context, uri)
        } else {
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private fun isChromeTabSupported(context: Context, uri: Uri): Boolean {
        val pm = context.packageManager
        val activityIntent = Intent(Intent.ACTION_VIEW, uri)
        val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)

        for (info in resolvedActivityList) {
            val serviceIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
            serviceIntent.setPackage(info.activityInfo.packageName)
            if (pm.resolveService(serviceIntent, 0) != null) {
                return true
            }
        }
        return false
    }
}