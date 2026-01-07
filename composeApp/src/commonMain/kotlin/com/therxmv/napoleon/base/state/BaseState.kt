package com.therxmv.napoleon.base.state

import androidx.compose.runtime.Stable

@Stable
sealed interface BaseState<out T> {

    data object Idle : BaseState<Nothing>

    data object Loading : BaseState<Nothing>

    data class Ready<out T>(val data: T, val cacheReason: String? = null) : BaseState<T>

    data class Error(val message: String, val onRetry: (() -> Unit)? = null) : BaseState<Nothing>
}