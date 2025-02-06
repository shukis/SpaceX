package com.spaceix.domain.model

import kotlinx.datetime.Instant

data class LaunchesEntity(
    val launches:List<LaunchEntity>
)

data class LaunchEntity(
    val flightNumber: Int,
    val missionName: String,
    val upcoming: Boolean,
    val launchDateUtc: Instant,
    val rocket: RocketEntity,
    val telemetry: TelemetryEntity,
    val launchSuccess: Boolean,
    val launchFailureDetails: LaunchFailureDetailsEntity?,
    val links: LinksEntity,
    val details: String?
)

data class RocketEntity(
    val rocketId: String,
    val rocketName: String,
    val rocketType: String?
)

data class TelemetryEntity(
    val flightClub: String?
)

data class LaunchFailureDetailsEntity(
    val reason: String?
)

data class LinksEntity(
    val missionPatchSmall: String?,
    val flickrImages: List<String>?
)