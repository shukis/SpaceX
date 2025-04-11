package com.spaceix.domain.usecase.launches

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.repository.SpaceXRepository

class MarkAsFavouriteLaunchUseCase(private val repository: SpaceXRepository) :
    UseCase.Completable<MarkAsFavouriteLaunchUseCase.Arg> {

    override suspend fun invoke(arg: Arg): Either<Unit> {
        return repository.upsertFavouriteLaunch(arg.id)
    }

    data class Arg(val id: String) : UseCase.Arg()
}