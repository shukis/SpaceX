package com.spaceix.data.model

import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.model.LaunchFailureDetailsEntity
import com.spaceix.domain.model.LinksEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class LaunchResponse(
    val rocket: String,
    val success: Boolean?,
    val failures: List<FailureResponse>?,
    val name: String,
    val dateUtc: Instant,
    val id: String,
    val flightNumber: Int,
    val upcoming: Boolean,
    val links: LinksResponse,
    val details: String?,
    val crew: List<String>?
) {
    fun toEntity() = LaunchEntity(
        id = id,
        name = name,
        flightNumber = flightNumber,
        upcoming = upcoming,
        launchDateUtc = dateUtc,
        rocketId = rocket,
        launchSuccess = success == true,
        launchFailureDetails = LaunchFailureDetailsEntity(failures?.firstOrNull()?.reason),
        links = links.toEntity(),
        details = details,
        crew = crew.orEmpty()
    )
}

@Serializable
data class LinksResponse(
    val patch: PatchResponse?,
    val flickr: FlickrResponse?,
    val webcast: String?,
    val article: String?,
    val wikipedia: String?,
    val reddit: RedditResponse?
) {
    fun toEntity() = LinksEntity(
        missionPatchSmall = patch?.small ?: "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png",
        flickrImages = flickr?.original,
        webcast = webcast,
        article = article,
        wikipedia = wikipedia,
        reddit = reddit?.campaign ?: reddit?.launch ?: reddit?.media ?: reddit?.recovery
    )
}

@Serializable
data class PatchResponse(val small: String?)

@Serializable
data class FlickrResponse(val original: List<String>?)

@Serializable
data class FailureResponse(
    val time: Int,
    val altitude: Int?,
    val reason: String
)

@Serializable
data class RedditResponse(
    val campaign: String?,
    val launch: String?,
    val media: String?,
    val recovery: String?
)