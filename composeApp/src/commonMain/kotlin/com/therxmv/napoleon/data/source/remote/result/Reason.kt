package com.therxmv.napoleon.data.source.remote.result

import io.ktor.client.call.NoTransformationFoundException
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.error_cant_find
import napoleon.leonres.generated.resources.error_generic
import napoleon.leonres.generated.resources.error_offline
import napoleon.leonres.generated.resources.error_slow_internet
import napoleon.leonres.generated.resources.fallback_cant_find
import napoleon.leonres.generated.resources.fallback_offline
import napoleon.leonres.generated.resources.fallback_slow_internet
import org.jetbrains.compose.resources.StringResource

sealed class Reason(open val messageRes: StringResource) {

    sealed class Error(override val messageRes: StringResource) : Reason(messageRes) {
        data object Offline : Error(Res.string.error_offline)
        data object CantFindData : Error(Res.string.error_cant_find)
        data object SlowInternet : Error(Res.string.error_slow_internet)
        data object Generic : Error(Res.string.error_generic)
    }

    sealed class Fallback(override val messageRes: StringResource) : Reason(messageRes) {
        data object Offline : Fallback(Res.string.fallback_offline)
        data object CantFindData : Fallback(Res.string.fallback_cant_find)
        data object SlowInternet : Fallback(Res.string.fallback_slow_internet)
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