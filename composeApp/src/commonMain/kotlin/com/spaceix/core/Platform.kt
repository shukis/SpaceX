package com.spaceix.core

object Platform {
    val isAndroid: Boolean get() = getPlatform() == PlatformType.Android
    val isIos: Boolean get() = getPlatform() == PlatformType.Ios
    val isDesktop: Boolean get() = getPlatform() == PlatformType.Desktop
}

enum class PlatformType {
    Android, Ios, Desktop
}

expect fun getPlatform(): PlatformType
expect fun isAndroid12OrAbove(): Boolean