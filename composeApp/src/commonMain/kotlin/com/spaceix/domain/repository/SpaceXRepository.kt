package com.spaceix.domain.repository

import com.spaceix.domain.Either

interface SpaceXRepository {
    suspend fun getAllLaunches(): Either<Unit>
    suspend fun getRockerDetails(id: String): Either<Unit>
}