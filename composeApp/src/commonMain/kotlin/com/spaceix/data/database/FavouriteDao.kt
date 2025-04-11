package com.spaceix.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.spaceix.domain.Either
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLaunchesDao {

    @Upsert
    suspend fun upsert(launch: FavouriteLaunchDbEntity)

    @Query("SELECT * FROM FavouriteLaunchDbEntity")
    suspend fun getFavoriteLaunches(): List<FavouriteLaunchDbEntity>

    @Query("DELETE FROM FavouriteLaunchDbEntity WHERE id = :id")
    suspend fun deleteFavoriteLaunch(id: String)
}

@Dao
interface FavouriteRocketsDao {

    @Upsert
    suspend fun upsert(rocket: FavouriteRocketDbEntity)

    @Query("SELECT * FROM FavouriteRocketDbEntity")
    suspend fun getFavoriteRockets(): List<FavouriteRocketDbEntity>

    @Query("DELETE FROM FavouriteRocketDbEntity WHERE id = :id")
    suspend fun deleteFavoriteRocket(id: String)
}