package com.spaceix.data.local

import com.spaceix.domain.model.RocketEntity

class RocketsCache {

    private var rockets: List<RocketEntity>? = null

    fun invalidate() {
        rockets = null
    }

    fun setRockets(rockets: List<RocketEntity>) {
        this.rockets = rockets
    }

    fun getRockets(): List<RocketEntity>? {
        return rockets
    }
}