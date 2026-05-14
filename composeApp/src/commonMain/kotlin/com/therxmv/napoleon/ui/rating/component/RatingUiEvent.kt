package com.therxmv.napoleon.ui.rating.component

sealed interface RatingUiEvent {

    data object AddSubjectInput : RatingUiEvent

    data class DeleteSubjectInput(val id: String) : RatingUiEvent

    data class UpdateSubjectInput(
        val id: String,
        val name: String? = null,
        val credits: String? = null,
        val score: String? = null,
    ) : RatingUiEvent

    data class UpdateProbabilityInput(
        val id: RatingUiData.ProbabilityInput.Id,
        val value: String,
    ) : RatingUiEvent
}