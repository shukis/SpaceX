package com.spaceix

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform