package com.spaceix.data.model

import com.spaceix.domain.model.CrewMemberEntity
import kotlinx.serialization.Serializable

@Serializable
data class CrewMemberResponse(
    val name: String,
    val id: String,
    val agency: String?,
    val image: String?,
    val wikipedia: String?,
    val status: String?,
) {
    fun toEntity() = CrewMemberEntity(
        id = id,
        name = name,
        agency = agency,
        image = image,
        wikipedia = wikipedia,
        status = status
    )
}