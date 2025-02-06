package com.spaceix.data.model

import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.model.LaunchFailureDetailsEntity
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.model.LinksEntity
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.model.TelemetryEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class LaunchesResponse(
    val launches: List<LaunchResponse>
) {
    fun toEntity() = LaunchesEntity(
        launches = launches.map { it.toEntity() }
    )
}

@Serializable
data class LaunchResponse(
    val flightNumber: Int,
    val missionName: String?,
    val upcoming: Boolean,
    val launchDateUtc: Instant,
    val rocket: RocketResponse,
    val telemetry: TelemetryResponse,
    val launchSuccess: Boolean?,
    val launchFailureDetails: LaunchFailureDetailsResponse?,
    val links: LinksResponse,
    val details: String?,
) {
    fun toEntity() = LaunchEntity(
        flightNumber = flightNumber,
        missionName = missionName.orEmpty(),
        upcoming = upcoming,
        launchDateUtc = launchDateUtc,
        rocket = rocket.toEntity(),
        telemetry = telemetry.toEntity(),
        launchSuccess = launchSuccess == true,
        launchFailureDetails = launchFailureDetails?.toEntity(),
        links = links.toEntity(),
        details = details
    )
}

@Serializable
data class RocketResponse(
    val rocketId: String,
    val rocketName: String?,
    val rocketType: String?
) {
    fun toEntity() = RocketEntity(
        rocketId = rocketId,
        rocketName = rocketName.orEmpty(),
        rocketType = rocketType
    )
}

@Serializable
data class TelemetryResponse(
    val flightClub: String?
) {
    fun toEntity() = TelemetryEntity(
        flightClub = flightClub
    )
}

@Serializable
data class LaunchFailureDetailsResponse(
    val reason: String?
) {
    fun toEntity() = LaunchFailureDetailsEntity(
        reason = reason
    )
}

@Serializable
data class LinksResponse(
    val missionPatchSmall: String?,
    val flickrImages: List<String>?
) {
    fun toEntity() = LinksEntity(
        missionPatchSmall = missionPatchSmall,
        flickrImages = flickrImages
    )
}