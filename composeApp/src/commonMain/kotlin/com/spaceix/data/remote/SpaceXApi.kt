package com.spaceix.data.remote

import com.spaceix.domain.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://api.spacexdata.com/v3"

class SpaceXApi(private val httpClient: HttpClient) {
    suspend fun getAllLaunches(): Unit {
        return httpClient.get("$BASE_URL/launches").body()
    }

    suspend fun getRocketDetails(id: String): Unit {
        return httpClient.get("$BASE_URL/rockets/$id").body()
    }

}