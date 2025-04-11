package com.spaceix.domain.usecase.launches

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.flatMap
import com.spaceix.domain.map
import com.spaceix.domain.model.LaunchesEntity
import com.spaceix.domain.repository.SpaceXRepository

class GetLaunchesUseCase(
    private val repository: SpaceXRepository,
    private val getFavouriteLaunchesUseCase: GetFavouriteLaunchesUseCase
) : UseCase.NoArg<LaunchesEntity> {

    override suspend fun invoke(): Either<LaunchesEntity> {
        return getFavouriteLaunchesUseCase().map { it.launches }
            .flatMap { favouriteLaunches ->
            repository.getAllLaunches().map {
                LaunchesEntity(it.launches.map { launch ->
                    launch.copy(isFavourite = favouriteLaunches.any { favouriteLaunch ->
                        favouriteLaunch.id == launch.id
                    })
                }.sortedByDescending { launch -> launch.flightNumber })
            }
        }
    }
}