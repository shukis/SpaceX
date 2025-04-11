package com.spaceix.data.model

import com.spaceix.domain.model.EnginesEntity
import com.spaceix.domain.model.PayloadWeightEntity
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.model.StageEntity
import kotlinx.serialization.Serializable

@Serializable
data class RocketResponse(
    val height: DimensionResponse?,
    val diameter: DimensionResponse?,
    val mass: MassResponse?,
    val firstStage: StageResponse?,
    val secondStage: StageResponse?,
    val engines: EnginesResponse?,
    val payloadWeights: List<PayloadWeightResponse?>?,
    val flickrImages: List<String?>?,
    val name: String?,
    val type: String?,
    val active: Boolean?,
    val stages: Int?,
    val boosters: Int?,
    val costPerLaunch: Long?,
    val successRatePct: Int?,
    val country: String?,
    val company: String?,
    val wikipedia: String?,
    val description: String?,
    val id: String
) {
    fun toEntity() = RocketEntity(
        height = height?.meters,
        diameter = diameter?.meters,
        mass = mass?.kg,
        firstStage = firstStage?.toEntity(),
        secondStage = secondStage?.toEntity(),
        engines = engines?.toEntity(),
        payloadWeights = payloadWeights?.map { it?.toEntity() }.orEmpty(),
        flickrImages = flickrImages.orEmpty(),
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        id = id
    )
}

@Serializable
data class DimensionResponse(
    val meters: Double?,
    val feet: Double?
)

@Serializable
data class MassResponse(
    val kg: Int,
    val lb: Int
)

@Serializable
data class StageResponse(
    val reusable: Boolean?,
    val engines: Int?,
    val fuelAmountTons: Double?,
    val burnTimeSec: Int?
) {
    fun toEntity() = StageEntity(reusable, engines, fuelAmountTons, burnTimeSec)
}

@Serializable
data class EnginesResponse(
    val number: Int?,
    val type: String?
) {
    fun toEntity() = EnginesEntity(number, type)
}

@Serializable
data class PayloadWeightResponse(
    val id: String,
    val name: String?,
    val kg: Int?
) {
    fun toEntity() = PayloadWeightEntity(id, name, kg)
}