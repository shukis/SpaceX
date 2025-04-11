package com.spaceix.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.spaceix.domain.model.FavouriteLaunchEntity
import com.spaceix.domain.model.FavouriteRocketEntity

@Entity
class FavouriteLaunchDbEntity(@PrimaryKey val id: String) {
    fun toEntity() = FavouriteLaunchEntity(id)
}

@Entity
class FavouriteRocketDbEntity(@PrimaryKey val id: String) {
    fun toEntity() = FavouriteRocketEntity(id)
}