package com.spaceix.domain.repository

import com.spaceix.domain.Either
import com.spaceix.domain.model.LaunchesEntity

interface SpaceXRepository {
    suspend fun getAllLaunches(): Either<LaunchesEntity>
    suspend fun getRockerDetails(id: String): Either<Unit>
}