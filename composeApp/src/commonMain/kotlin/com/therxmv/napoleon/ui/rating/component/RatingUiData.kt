package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Immutable
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.ui.rating.component.RatingUiData.SubjectInput
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class RatingUiData(
    val nameLabel: String,
    val creditsLabel: String,
    val scoreLabel: String,
    val addInputLabel: String,
    val subjectInputs: List<SubjectInput>,
    val ratingResult: String,
    val probabilityInputs: List<ProbabilityInput>,
    val probabilityResult: String,
    val infoData: Info,
) {
    @OptIn(ExperimentalUuidApi::class)
    data class SubjectInput(
        val id: String = Uuid.random().toHexDashString(),
        val name: String = Res.string.rating_name_label,
        val credits: String = "3",
        val score: String = "74",
        val error: String? = null,
    )

    @OptIn(ExperimentalUuidApi::class)
    data class ProbabilityInput(
        val title: String,
        val value: String,
        val error: String? = null,
    ) {
        enum class Id(val title: String) {
            Capacity(Res.string.rating_capacity),
            Quota(Res.string.rating_quota),
            Average(Res.string.rating_average),
            Deviation(Res.string.rating_deviation),
        }
    }

    data class Info(
        val text: String,
        val link: String,
        val linkText: String,
    )
}

fun RatingUiData.toModel(): RatingModel =
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