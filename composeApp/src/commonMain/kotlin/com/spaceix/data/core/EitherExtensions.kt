package com.spaceix.data.core

import coil3.network.HttpException
import com.spaceix.domain.Either
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.IOException

suspend inline fun <T : Any> apiCall(
    crossinline producer: suspend () -> T
) = Either {
    withContext(Dispatchers.IO) {
        try {
            producer()
        } catch (e: SocketTimeoutException) {
            throw RemoteException(DataFailure.REQUEST_TIMEOUT, e)
        } catch (e: UnresolvedAddressException) {
            throw RemoteException(DataFailure.NO_INTERNET, e)
        } catch (e: Exception) {
            throw RemoteException(DataFailure.UNKNOWN, e)
        }
    }
}.mapException()

fun <T> Either<T>.mapException(): Either<T> {
    return when (this) {
        is Either.Success -> this
        is Either.Failure -> {
            val code = (exception as? HttpException)?.response?.code
            when (code) {
                in 200..299 -> RemoteException(DataFailure.SERIALIZATION, exception.cause)
                408 -> RemoteException(DataFailure.REQUEST_TIMEOUT)
                429 -> RemoteException(DataFailure.TOO_MANY_REQUESTS)
                in 500..599 -> RemoteException(DataFailure.SERVER)
                else -> RemoteException(DataFailure.UNKNOWN)
            }.let { Either.Failure(it) }
        }
    }
}

open class RemoteException(failure: DataFailure, throwable: Throwable? = null) :
    IOException("RemoteException: ${failure.name}\n${throwable?.message}")

enum class DataFailure {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER,
    SERIALIZATION,
    UNKNOWN
}

