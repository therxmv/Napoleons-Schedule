package com.therxmv.leonui.state

import androidx.compose.runtime.Stable

@Stable
sealed interface LeonState<out T> {

    data object Idle : LeonState<Nothing>

    data object Loading : LeonState<Nothing>

    data class Ready<out T>(val data: T, val cacheReason: String? = null) : LeonState<T>

    data class Error(val message: String, val onRetry: (() -> Unit)? = null) : LeonState<Nothing>
}