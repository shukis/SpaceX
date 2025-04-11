package com.spaceix.domain.repository

import com.spaceix.domain.Either
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.model.FavouriteLaunchesEntity
import com.spaceix.domain.model.FavouriteRocketsEntity
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.model.RocketsEntity

interface SpaceXRepository {
    suspend fun getAllLaunches(): Either<LaunchesEntity>
    suspend fun getAllRockets(): Either<RocketsEntity>
    suspend fun getFavouriteLaunches(): Either<FavouriteLaunchesEntity>
    suspend fun getFavouriteRockets(): Either<FavouriteRocketsEntity>
    suspend fun upsertFavouriteLaunch(id: String): Either<Unit>
    suspend fun upsertFavouriteRocket(id: String): Either<Unit>
    suspend fun deleteFavouriteLaunch(id: String): Either<Unit>
    suspend fun deleteFavouriteRocket(id: String): Either<Unit>
    suspend fun getCrewMember(id: String): Either<CrewMemberEntity>
}