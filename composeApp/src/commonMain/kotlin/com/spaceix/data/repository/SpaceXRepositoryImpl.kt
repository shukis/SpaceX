package com.spaceix.data.repository

import com.spaceix.data.core.apiCall
import com.spaceix.data.database.FavouriteLaunchDbEntity
import com.spaceix.data.database.FavouriteLaunchesDao
import com.spaceix.data.database.FavouriteRocketDbEntity
import com.spaceix.data.database.FavouriteRocketsDao
import com.spaceix.data.local.LaunchesCache
import com.spaceix.data.local.RocketsCache
import com.spaceix.data.remote.SpaceXApi
import com.spaceix.domain.Either
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.model.FavouriteLaunchesEntity
import com.spaceix.domain.model.FavouriteRocketsEntity
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.model.RocketsEntity
import com.spaceix.domain.repository.SpaceXRepository

class SpaceXRepositoryImpl(
    private val favouriteLaunchesDao: FavouriteLaunchesDao,
    private val favouriteRocketsDao: FavouriteRocketsDao,
    private val launchesCache: LaunchesCache,
    private val rocketsCache: RocketsCache,
    private val api: SpaceXApi
) : SpaceXRepository {

    override suspend fun getAllLaunches(): Either<LaunchesEntity> {
        return launchesCache.getLaunches()?.let {
            Either { LaunchesEntity(it) }
        } ?: apiCall {
            api.getAllLaunches().map { it.toEntity() }.also {
                launchesCache.setLaunches(it)
            }.let { LaunchesEntity(it) }
        }
    }

    override suspend fun getAllRockets(): Either<RocketsEntity> {
        return rocketsCache.getRockets()?.let {
            Either { RocketsEntity(it) }
        } ?: apiCall {
            api.getAllRockets().map { it.toEntity() }.also {
                rocketsCache.setRockets(it)
            }.let { RocketsEntity(it) }
        }
    }

    override suspend fun getFavouriteLaunches(): Either<FavouriteLaunchesEntity> {
        return Either {
            FavouriteLaunchesEntity(
                favouriteLaunchesDao.getFavoriteLaunches().map { it.toEntity() })
        }
    }

    override suspend fun getFavouriteRockets(): Either<FavouriteRocketsEntity> {
        return Either {
            FavouriteRocketsEntity(
                favouriteRocketsDao.getFavoriteRockets().map { it.toEntity() })
        }
    }

    override suspend fun upsertFavouriteLaunch(id: String): Either<Unit> {
        return Either { favouriteLaunchesDao.upsert(FavouriteLaunchDbEntity(id)) }
    }

    override suspend fun upsertFavouriteRocket(id: String): Either<Unit> {
        return Either { favouriteRocketsDao.upsert(FavouriteRocketDbEntity(id)) }
    }

    override suspend fun deleteFavouriteLaunch(id: String): Either<Unit> {
        return Either { favouriteLaunchesDao.deleteFavoriteLaunch(id) }
    }

    override suspend fun deleteFavouriteRocket(id: String): Either<Unit> {
        return Either { favouriteRocketsDao.deleteFavoriteRocket(id) }
    }

    override suspend fun getCrewMember(id: String): Either<CrewMemberEntity> {
        return apiCall { api.getCrewMember(id).toEntity() }
    }
}