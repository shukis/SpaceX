package com.spaceix.domain.usecase.rockets

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.repository.SpaceXRepository

class DeleteFromFavouriteRocketsUseCase(private val repository: SpaceXRepository) :
    UseCase.Completable<DeleteFromFavouriteRocketsUseCase.Arg> {

    override suspend fun invoke(arg: Arg): Either<Unit> {
        return repository.deleteFavouriteRocket(arg.id)
    }

    data class Arg(val id: String) : UseCase.Arg()
}