package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Immutable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class RatingUiState(
    val nameLabel: String,
    val creditsLabel: String,
    val scoreLabel: String,
    val addInputLabel: String,
    val subjects: List<Subject>,
    val result: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    data class Subject(
        val id: String = Uuid.random().toHexDashString(),
        val name: String = "Предмет", // TODO translate
        val credits: String = "3",
        val score: String = "60",
        val error: String? = null,
    )
}