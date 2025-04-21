package com.spaceix.core

actual fun getPlatform(): PlatformType = PlatformType.Ios
actual fun isAndroid12OrAbove() = false