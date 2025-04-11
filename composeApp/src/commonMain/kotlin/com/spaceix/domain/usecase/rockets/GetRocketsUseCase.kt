package com.spaceix.domain.usecase.rockets

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.flatMap
import com.spaceix.domain.map
import com.spaceix.domain.model.RocketsEntity
import com.spaceix.domain.repository.SpaceXRepository

class GetRocketsUseCase(
    private val repository: SpaceXRepository,
    private val getFavouriteRocketsUseCase: GetFavouriteRocketsUseCase
) : UseCase.NoArg<RocketsEntity> {

    override suspend fun invoke(): Either<RocketsEntity> {
        return getFavouriteRocketsUseCase().map { it.rockets }
            .flatMap { favouriteRockets ->
                repository.getAllRockets().map {
                    RocketsEntity(it.rockets.map { rocket ->
                        rocket.copy(isFavourite = favouriteRockets.any { favouriteRocket ->
                            favouriteRocket.id == rocket.id
                        })
                    }.sortedByDescending { rocket -> rocket.name })
                }
            }
    }
}