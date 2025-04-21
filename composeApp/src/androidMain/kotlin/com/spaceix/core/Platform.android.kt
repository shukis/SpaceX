package com.spaceix.core

import android.os.Build

actual fun getPlatform(): PlatformType = PlatformType.Android
actual fun isAndroid12OrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S