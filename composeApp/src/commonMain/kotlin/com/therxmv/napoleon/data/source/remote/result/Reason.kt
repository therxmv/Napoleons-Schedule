package com.therxmv.napoleon.data.source.remote.result

import com.therxmv.napoleon.Res
import io.ktor.client.call.NoTransformationFoundException

sealed class Reason(open val message: String) {

    sealed class Error(override val message: String) : Reason(message) {
        data object Offline : Error(Res.string.error_offline)
        data object CantFindData : Error(Res.string.error_cant_find)
        data object SlowInternet : Error(Res.string.error_slow_internet)
        data object Generic : Error(Res.string.error_generic)
    }

    sealed class Fallback(override val message: String) : Reason(message) {
        data object Offline : Error(Res.string.fallback_offline)
        data object CantFindData : Error(Res.string.fallback_cant_find)
        data object SlowInternet : Error(Res.string.fallback_slow_internet)
    }

    companion object {
        fun errorFrom(exception: Throwable): Reason =
            when {
                exception.isSocketTimeoutException() -> Error.SlowInternet

                exception.isUnknownHostException() -> Error.Offline

                exception is NoTransformationFoundException -> Error.CantFindData

                else -> Error.Generic
            }

        fun fallbackFrom(exception: Throwable): Reason =
            when {
                exception.isSocketTimeoutException() -> Fallback.SlowInternet

                exception.isUnknownHostException() -> Fallback.Offline

                exception is NoTransformationFoundException -> Fallback.CantFindData

                else -> Error.Generic
            }
    }
}

// Java Exceptions is not available here. Might not work on iOS
private fun Throwable.isUnknownHostException(): Boolean =
    this::class.simpleName == "UnknownHostException"

private fun Throwable.isSocketTimeoutException(): Boolean =
    this::class.simpleName == "SocketTimeoutException"