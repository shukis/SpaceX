package com.spaceix.domain.usecase.launches

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.map
import com.spaceix.domain.model.LaunchEntity

class GetLaunchUseCase(private val getLaunchesUseCase: GetLaunchesUseCase) :
    UseCase<GetLaunchUseCase.Arg, LaunchEntity> {

    override suspend fun invoke(arg: Arg): Either<LaunchEntity> {
        return getLaunchesUseCase().map { it.launches.first { launch -> launch.id == arg.id } }
    }

    data class Arg(val id: String) : UseCase.Arg()
}