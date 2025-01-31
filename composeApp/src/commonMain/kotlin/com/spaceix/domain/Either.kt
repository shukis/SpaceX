package com.spaceix.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

/**
 * Either is an abstraction over an operation result.
 *
 * Either content can be transformed with [map] and [flatMap] and unwrapped with [unwrap],
 * or [getContentOrNull].
 *
 * */
sealed class Either<out T> {
    data class Success<T>(val content: T) : Either<T>() {
        init {
            checkNestedEither()
        }
    }

    data class Failure(val exception: Throwable) : Either<Nothing>()
}

/**
 * Nested [Either] is prohibited as it can lead to lost exceptions.
 * */
fun <T> Either<T>.checkNestedEither(): Either<T> {
    if (this is Either.Success<*>) check(content !is Either<*>) { "Nested Either is prohibited: $this" }
    return this
}

/**
 * Wrap an operation with [Either].
 * Any exception but [CancellationException] thrown inside the block will be caught & wrapped with an [Either.Failure].
 * [CancellationException] must be thrown to allow coroutines to cancel themselves.
 *
 * @return [Either.Success] on successful completion or [Either.Failure] if an exception has been thrown
 * inside the block.
 * */
@Suppress("FunctionName", "RedundantSuppression")
inline fun <T> Either(producer: () -> T): Either<T> = try {
    Either.Success(producer()).checkNestedEither()
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Either.Failure(e)
}.checkNestedEither()

/**
 * Maps the given function to the value from this [Either.Success] or returns this if this is [Either.Failure]
 * */
inline fun <T, R> Either<T>.map(f: (T) -> R): Either<R> = when (this) {
    is Either.Success -> Either { f(content) }
    is Either.Failure -> this
}.checkNestedEither()

/**
 * Returns the given function applied to the value from this [Either.Success]
 * or returns this if this is [Either.Failure].
 * */
inline fun <T, R> Either<T>.flatMap(f: (T) -> Either<R>): Either<R> = when (this) {
    is Either.Success -> try {
        f(content)
    } catch (e: Throwable) {
        Either.Failure(e)
    }

    is Either.Failure -> this
}.checkNestedEither()

/**
 * Maps the given function to the exception from this [Either.Failure] or returns this if this is [Either.Success]
 * */
inline fun <T> Either<T>.mapException(f: (Throwable) -> Throwable): Either<T> = when (this) {
    is Either.Success -> this
    is Either.Failure -> Either.Failure(f(exception))
}.checkNestedEither()

/**
 * Replace value of the [Either] with [Unit].
 * */
fun <T> Either<T>.ignoreValue(): Either<Unit> = when (this) {
    is Either.Success -> Either.Success(Unit)
    is Either.Failure -> this
}

/**
 * Returns the content if it is [Either.Success] or null if it is [Either.Failure]
 * */
fun <T> Either<T>.getContentOrNull(): T? = when (this) {
    is Either.Success -> content
    is Either.Failure -> null
}

/**
 * Calls the callback corresponding to the [Either] type.
 * */
inline fun <T> Either<T>.unwrap(
    onSuccess: (T) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    when (this) {
        is Either.Success -> onSuccess(content)
        is Either.Failure -> onFailure(exception)
    }
}

suspend inline fun <T : Any> retryEither(
    retryCount: Long = 3L,
    retryDelay: Long = 100L,
    producer: () -> Either<T>
): Either<T> {
    for (attempt in 1..retryCount) {
        val result = producer()
        if (result is Either.Success || attempt == retryCount) {
            return result
        }
        delay(retryDelay)
    }
    throw IllegalStateException("Should never happen")
}