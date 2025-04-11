package com.spaceix.domain.model

import com.spaceix.domain.base.Entity

data class RocketsEntity(val rockets: List<RocketEntity>) : Entity

data class RocketEntity(
    val height: Double?,
    val diameter: Double?,
    val mass: Int?,
    val firstStage: StageEntity?,
    val secondStage: StageEntity?,
    val engines: EnginesEntity?,
    val payloadWeights: List<PayloadWeightEntity?>,
    val flickrImages: List<String?>,
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
    val id: String,
    val isFavourite: Boolean = false
) : Entity

data class StageEntity(
    val reusable: Boolean?, val engines: Int?, val fuelAmountTons: Double?, val burnTimeSec: Int?
) : Entity

data class EnginesEntity(
    val number: Int?, val type: String?
) : Entity

data class PayloadWeightEntity(
    val id: String, val name: String?, val kg: Int?
) : Entity