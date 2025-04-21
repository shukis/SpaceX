package com.spaceix.core

actual fun getPlatform(): PlatformType = PlatformType.Desktop
actual fun isAndroid12OrAbove() = false