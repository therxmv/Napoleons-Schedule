package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Immutable
import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.ui.rating.component.RatingUiState.SubjectInput
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class RatingUiState(
    val nameLabel: String,
    val creditsLabel: String,
    val scoreLabel: String,
    val addInputLabel: String,
    val subjectInputs: List<SubjectInput>,
    val ratingResult: String,
    val probabilityInputs: List<ProbabilityInput>,
    val probabilityResult: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    data class SubjectInput(
        val id: String = Uuid.random().toHexDashString(),
        val name: String = "Предмет", // TODO translate
        val credits: String = "3",
        val score: String = "60",
        val error: String? = null,
    )

    @OptIn(ExperimentalUuidApi::class)
    data class ProbabilityInput(
        val title: String,
        val value: String,
        val error: String? = null,
    ) {
        enum class Id(val title: String) { // TODO translate
            Capacity("Кількість студентів"),
            Quota("Квота на стипендію"),
            Average("Середній бал групи"),
            Deviation("Відхилення балу"),
        }
    }
}

fun RatingUiState.toModel(): RatingModel =
    RatingModel(
        subjects = subjectInputs.toModel(),
    )

fun List<SubjectInput>.toModel(): List<SubjectModel> =
    map {
        SubjectModel(
            id = it.id,
            name = it.name,
            credits = it.credits,
            score = it.score,
            error = it.error,
        )
    }

fun List<SubjectModel>.toUi(): List<SubjectInput> =
    map {
        SubjectInput(
            id = it.id,
            name = it.name,
            credits = it.credits,
            score = it.score,
            error = it.error,
        )
    }