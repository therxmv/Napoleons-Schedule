package com.therxmv.napoleon.data.source.remote.result

sealed interface Result<out T> {

    data class Success<out T>(val data: T, val reason: Reason? = null) : Result<T>

    data class Failure(val reason: Reason) : Result<Nothing>

    companion object {
        inline fun <T> of(block: () -> T, fallbackBlock: () -> T): Result<T> =
            runCatching(block)
                .fold(
                    onFailure = { exception ->
                        exception.printStackTrace()
                        runCatching(fallbackBlock)
                            .fold(
                                onSuccess = { Success(data = it, reason = Reason.fallbackFrom(exception)) },
                                onFailure = { Failure(reason = Reason.errorFrom(exception)) },
                            )
                    },
                    onSuccess = {
                        Success(data = it)
                    }
                )
    }
}