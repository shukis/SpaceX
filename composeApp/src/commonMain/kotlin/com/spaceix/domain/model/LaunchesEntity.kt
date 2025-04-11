package com.spaceix.domain.model

import com.spaceix.domain.base.Entity
import kotlinx.datetime.Instant

data class LaunchesEntity(
    val launches: List<LaunchEntity>
) : Entity

data class LaunchEntity(
    val id: String,
    val name: String,
    val flightNumber: Int,
    val upcoming: Boolean,
    val launchDateUtc: Instant,
    val rocketId: String?,
    val launchSuccess: Boolean,
    val launchFailureDetails: LaunchFailureDetailsEntity?,
    val links: LinksEntity,
    val details: String?,
    val crew: List<String>,
    val isFavourite: Boolean = false
) : Entity

data class LaunchFailureDetailsEntity(
    val reason: String?
) : Entity

data class LinksEntity(
    val missionPatchSmall: String,
    val flickrImages: List<String>?,
    val webcast: String?,
    val article: String?,
    val wikipedia: String?,
    val reddit: String?
) : Entity

data class FavouriteLaunchesEntity(
    val launches: List<FavouriteLaunchEntity>
): Entity

data class FavouriteLaunchEntity(val id: String): Entity

data class FavouriteRocketsEntity(
    val rockets: List<FavouriteRocketEntity>
): Entity

data class FavouriteRocketEntity(val id: String): Entity