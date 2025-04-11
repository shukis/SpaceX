package com.spaceix.domain.base

import com.spaceix.domain.Either

fun interface UseCase<A : UseCase.Arg, R : Entity?> {
    open class Arg

    suspend operator fun invoke(arg: A): Either<R>

    fun interface NoArg<R : Entity?> {
        suspend operator fun invoke(): Either<R>
    }

    fun interface Completable<A : UseCase.Arg> {
        suspend operator fun invoke(arg: A): Either<Unit>
    }
}