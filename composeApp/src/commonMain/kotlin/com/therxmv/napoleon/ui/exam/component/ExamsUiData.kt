package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.therxmv.datetime.DateTimeConstants
import com.therxmv.datetime.toMillis
import com.therxmv.leonres.getSyncString
import com.therxmv.napoleon.data.repository.specialty.model.ExamModel
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.repository.specialty.model.ZalikModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class ExamsUiData(
    val infoData: Info,
    val sections: List<Section>,
    val datePickerData: DatePicker? = null,
) {

    data class Info(
        val textRes: StringResource,
        val link: String,
        val linkTextRes: StringResource,
    )

    data class DatePicker(
        val sectionId: Section.Id,
        val itemId: String,
        val date: LocalDate,
    )

    @Immutable
    data class Section(
        val id: Id,
        val titleRes: StringResource,
        val items: List<Item>,
    ) {
        override fun toString(): String =
            "${getSyncString(titleRes)}:\n${items.joinToString("\n") { it.toString() }}"

        enum class Id { Exam, Zalik }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Stable
    sealed interface Item {

        val id: String
        val name: String

        @Stable
        sealed interface Editable : Item {
            val isEditing: Boolean
            fun toggleEdit(isEditing: Boolean): Item

            data class Exam(
                override val id: String = Uuid.random().toHexDashString(),
                override val name: String,
                override val isEditing: Boolean = false,
                val teacher: String,
                val date: LocalDate,
            ) : Editable {
                override fun toggleEdit(isEditing: Boolean): Item = copy(isEditing = isEditing)

                override fun toString(): String =
                    "${date.format(DateTimeConstants.Format.dayDotMonthDotYear)} - $name, $teacher"
            }

            data class Zalik(
                override val id: String = Uuid.random().toHexDashString(),
                override val name: String,
                override val isEditing: Boolean = false,
            ) : Editable {
                override fun toggleEdit(isEditing: Boolean): Item = copy(isEditing = isEditing)

                override fun toString(): String = name
            }
        }

        data class EmptyPlaceholder(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
        ) : Item {
            override fun toString(): String = name
        }
    }
}

fun ExamsUiData.toModel(): ExamsModel {
    val exams = sections
        .first { it.id == ExamsUiData.Section.Id.Exam }
        .items
        .filterIsInstance<ExamsUiData.Item.Editable.Exam>()
        .map {
            ExamModel(
                teacher = it.teacher,
                lesson = it.name,
                dateMillis = it.date.toMillis(),
            )
        }
    val zaliks = sections
        .first { it.id == ExamsUiData.Section.Id.Zalik }
        .items
        .filterIsInstance<ExamsUiData.Item.Editable.Zalik>()
        .map {
            ZalikModel(lesson = it.name)
        }

    return ExamsModel(exams, zaliks)
}