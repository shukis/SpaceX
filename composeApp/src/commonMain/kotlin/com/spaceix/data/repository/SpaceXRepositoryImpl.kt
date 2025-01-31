package com.spaceix.data.repository

import com.spaceix.data.core.apiCall
import com.spaceix.data.remote.SpaceXApi
import com.spaceix.domain.Either
import com.spaceix.domain.repository.SpaceXRepository

class SpaceXRepositoryImpl(
    private val api: SpaceXApi
) : SpaceXRepository {

    override suspend fun getAllLaunches(): Either<Unit> {
        return apiCall { api.getAllLaunches() }
    }

    override suspend fun getRockerDetails(id: String): Either<Unit> {
        return apiCall { api.getRocketDetails(id) }
    }
}