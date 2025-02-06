package com.spaceix.data.repository

import com.spaceix.data.core.apiCall
import com.spaceix.data.remote.SpaceXApi
import com.spaceix.domain.Either
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.repository.SpaceXRepository

class SpaceXRepositoryImpl(
    private val api: SpaceXApi
) : SpaceXRepository {

    override suspend fun getAllLaunches(): Either<LaunchesEntity> {
        return apiCall { api.getAllLaunches().toEntity() }
    }

    override suspend fun getRockerDetails(id: String): Either<Unit> {
        return apiCall { api.getRocketDetails(id) }
    }
}