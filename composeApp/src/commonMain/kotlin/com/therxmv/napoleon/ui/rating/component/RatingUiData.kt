package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Immutable
import com.therxmv.leonres.getSyncString
import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.ui.rating.component.RatingUiData.SubjectInput
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.rating_average
import napoleon.leonres.generated.resources.rating_capacity
import napoleon.leonres.generated.resources.rating_deviation
import napoleon.leonres.generated.resources.rating_name_label
import napoleon.leonres.generated.resources.rating_quota
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class RatingUiData(
    val nameLabelRes: StringResource,
    val creditsLabelRes: StringResource,
    val scoreLabelRes: StringResource,
    val addInputLabelRes: StringResource,
    val subjectInputs: List<SubjectInput>,
    val ratingResult: String,
    val probabilityInputs: List<ProbabilityInput>,
    val probabilityResult: String,
    val infoData: Info,
) {
    @OptIn(ExperimentalUuidApi::class)
    data class SubjectInput(
        val id: String = Uuid.random().toHexDashString(),
        val name: String = getSyncString(Res.string.rating_name_label),
        val credits: String = "3",
        val score: String = "74",
        val error: String? = null,
    )

    @OptIn(ExperimentalUuidApi::class)
    data class ProbabilityInput(
        val id: Id,
        val value: String,
        val error: String? = null,
    ) {
        enum class Id(val titleRes: StringResource) {
            Capacity(Res.string.rating_capacity),
            Quota(Res.string.rating_quota),
            Average(Res.string.rating_average),
            Deviation(Res.string.rating_deviation),
        }
    }

    data class Info(
        val textRes: StringResource,
        val link: String,
        val linkTextRes: StringResource,
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