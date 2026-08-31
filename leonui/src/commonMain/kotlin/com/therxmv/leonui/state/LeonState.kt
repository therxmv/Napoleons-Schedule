package com.therxmv.leonui.state

import androidx.compose.runtime.Stable
import org.jetbrains.compose.resources.StringResource

@Stable
sealed interface LeonState<out T> {

    data object Idle : LeonState<Nothing>

    data object Loading : LeonState<Nothing>

    data class Ready<out T>(val data: T, val cacheReasonRes: StringResource? = null) : LeonState<T>

    data class Error(val messageRes: StringResource, val onRetry: (() -> Unit)? = null) : LeonState<Nothing>
}

inline fun <T> LeonState<T>.mapReady(crossinline transform: (T) -> T): LeonState<T> =
    when (this) {
        is LeonState.Ready -> copy(data = transform(data))
        else -> this
    }