package com.therxmv.napoleon.ui.rating.component

sealed interface RatingUiEvent {

    data object AddInput : RatingUiEvent

    data class DeleteInput(val id: String) : RatingUiEvent

    data class UpdateInput(
        val id: String,
        val name: String? = null,
        val credits: String? = null,
        val score: String? = null,
    ) : RatingUiEvent
}