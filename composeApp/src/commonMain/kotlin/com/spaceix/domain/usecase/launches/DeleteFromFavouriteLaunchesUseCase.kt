package com.spaceix.domain.usecase.launches

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.repository.SpaceXRepository

class DeleteFromFavouriteLaunchesUseCase(private val repository: SpaceXRepository) :
    UseCase.Completable<DeleteFromFavouriteLaunchesUseCase.Arg> {

    override suspend fun invoke(arg: Arg): Either<Unit> {
        return repository.deleteFavouriteLaunch(arg.id)
    }

    data class Arg(val id: String) : UseCase.Arg()
}