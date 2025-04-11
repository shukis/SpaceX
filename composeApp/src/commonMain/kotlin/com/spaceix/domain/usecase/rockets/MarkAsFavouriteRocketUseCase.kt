package com.spaceix.domain.usecase.rockets

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.repository.SpaceXRepository

class MarkAsFavouriteRocketUseCase(private val repository: SpaceXRepository) :
    UseCase.Completable<MarkAsFavouriteRocketUseCase.Arg> {

    override suspend fun invoke(arg: Arg): Either<Unit> {
        return repository.upsertFavouriteRocket(arg.id)
    }

    data class Arg(val id: String) : UseCase.Arg()
}