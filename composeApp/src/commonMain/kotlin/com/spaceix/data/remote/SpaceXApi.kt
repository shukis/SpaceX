package com.spaceix.data.remote

import com.spaceix.data.model.CrewMemberResponse
import com.spaceix.data.model.LaunchResponse
import com.spaceix.data.model.RocketResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://api.spacexdata.com/v4"

class SpaceXApi(private val httpClient: HttpClient) {
    suspend fun getAllLaunches(): List<LaunchResponse> {
        return httpClient.get("$BASE_URL/launches/past").body()
    }

    suspend fun getAllRockets(): List<RocketResponse> {
        return httpClient.get("$BASE_URL/rockets").body()
    }

    suspend fun getCrewMember(id: String): CrewMemberResponse {
        return httpClient.get("$BASE_URL/crew/$id").body()
    }

}