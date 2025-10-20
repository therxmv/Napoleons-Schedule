package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Immutable
import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.ui.rating.component.RatingUiState.Subject
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

fun RatingUiState.toModel(): RatingModel =
    RatingModel(
        subjects = subjects.toModel(),
    )

fun List<Subject>.toModel(): List<SubjectModel> =
    map {
        SubjectModel(
            id = it.id,
            name = it.name,
            credits = it.credits,
            score = it.score,
            error = it.error,
        )
    }

fun List<SubjectModel>.toUi(): List<Subject> =
    map {
        Subject(
            id = it.id,
            name = it.name,
            credits = it.credits,
            score = it.score,
            error = it.error,
        )
    }