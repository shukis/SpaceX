package com.spaceix.domain.usecase.launches

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.model.FavouriteLaunchesEntity
import com.spaceix.domain.repository.SpaceXRepository

class GetFavouriteLaunchesUseCase(private val repository: SpaceXRepository) :
    UseCase.NoArg<FavouriteLaunchesEntity> {

    override suspend operator fun invoke(): Either<FavouriteLaunchesEntity> {
        return repository.getFavouriteLaunches()
    }
}