package com.spaceix.data.local

import com.spaceix.domain.model.LaunchEntity

class LaunchesCache {

    private var launches: List<LaunchEntity>? = null

    fun invalidate() {
        launches = null
    }

    fun setLaunches(launches: List<LaunchEntity>) {
        this.launches = launches
    }

    fun getLaunches(): List<LaunchEntity>? {
        return launches
    }
}