package com.spaceix.domain.model

import com.spaceix.domain.base.Entity

data class CrewMemberEntity(
    val name: String,
    val agency: String?,
    val image: String?,
    val wikipedia: String?,
    val status: String?,
    val id: String
): Entity