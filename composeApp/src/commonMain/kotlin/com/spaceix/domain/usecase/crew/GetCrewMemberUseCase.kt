package com.spaceix.domain.usecase.crew

import com.spaceix.domain.Either
import com.spaceix.domain.base.UseCase
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.repository.SpaceXRepository
import kotlinx.coroutines.delay

class GetCrewMemberUseCase(private val repository: SpaceXRepository) :
    UseCase<GetCrewMemberUseCase.Arg, CrewMemberEntity> {

    override suspend fun invoke(arg: Arg): Either<CrewMemberEntity> {
        delay(2000) // emulate network delay
        return repository.getCrewMember(arg.id)
    }

    data class Arg(val id: String) : UseCase.Arg()
}