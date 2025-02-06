package com.spaceix.data.repository

import com.spaceix.data.local.mockLaunchesResponse
import com.spaceix.data.model.LaunchResponse
import com.spaceix.domain.Either
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.repository.SpaceXRepository
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class MockSpaceXRepositoryImpl : SpaceXRepository {

    @ExperimentalSerializationApi
    override suspend fun getAllLaunches(): Either<LaunchesEntity> {
        val json = Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
            explicitNulls = false

        }
        val response = json.decodeFromString<List<LaunchResponse>>(mockLaunchesResponse)
        delay(5000L)
        return Either { LaunchesEntity(launches = response.map { it.toEntity() }) }
    }

    override suspend fun getRockerDetails(id: String): Either<Unit> {
        return Either { }
    }
}