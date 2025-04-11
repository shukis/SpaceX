package com.spaceix.domain.usecase.rockets

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.map
import com.spaceix.domain.model.RocketEntity

class GetRocketUseCase(private val getRocketsUseCase: GetRocketsUseCase) :
    UseCase<GetRocketUseCase.Arg, RocketEntity> {

    override suspend fun invoke(arg: Arg): Either<RocketEntity> {
        return getRocketsUseCase().map { it.rockets.first { rocket -> rocket.id == arg.id } }
    }

    data class Arg(val id: String) : UseCase.Arg()
}