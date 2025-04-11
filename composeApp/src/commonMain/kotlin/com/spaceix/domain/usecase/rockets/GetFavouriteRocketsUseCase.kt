package com.spaceix.domain.usecase.rockets

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.model.FavouriteRocketsEntity
import com.spaceix.domain.repository.SpaceXRepository

class GetFavouriteRocketsUseCase(private val repository: SpaceXRepository) :
    UseCase.NoArg<FavouriteRocketsEntity> {

    override suspend operator fun invoke(): Either<FavouriteRocketsEntity> {
        return repository.getFavouriteRockets()
    }
}