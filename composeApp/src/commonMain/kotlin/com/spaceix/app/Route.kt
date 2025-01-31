package com.spaceix.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object SpaceXGraph: Route

    @Serializable
    data object RocketList: Route

    @Serializable
    data class RocketDetails(val id: String): Route
}