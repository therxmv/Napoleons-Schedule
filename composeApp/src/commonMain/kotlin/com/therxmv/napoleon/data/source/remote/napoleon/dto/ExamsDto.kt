package com.therxmv.napoleon.data.source.remote.napoleon.dto

import com.therxmv.datetime.DateTimeConstants
import com.therxmv.datetime.getNowDate
import com.therxmv.datetime.getNowMillis
import com.therxmv.datetime.toDate
import com.therxmv.datetime.toMillis
import com.therxmv.napoleon.data.repository.specialty.model.ExamModel
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.repository.specialty.model.ZalikModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.number
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExamsDto(
    @SerialName("exams") val exams: List<ExamItemDto>,
    @SerialName("zalik") val zalik: List<ZalikItemDto>,
)

@Serializable
data class ExamItemDto(
    @SerialName("teacher") val teacher: String,
    @SerialName("lesson") val lesson: String,
    @SerialName("date") val date: String,
)

@Serializable
data class ZalikItemDto(
    @SerialName("lesson") val lesson: String,
)

fun ExamsDto.toModel(): ExamsModel =
    ExamsModel(
        exams = exams.map { it.toModel() },
        zalik = zalik.map { it.toModel() },
    )

fun ExamItemDto.toModel(): ExamModel {
    val dateMillis = try {
        val today = getNowDate()
        val (day, month) = date.split('.').map { it.toInt() }
        val year = when {
            month <= 2 && today.month.number >= 9 -> today.year + 1

            month >= 9 && today.month.number <= 2 -> today.year - 1

            else -> today.year
        }
        LocalDate(year, month, day).toMillis()
    } catch (_: Exception) {
        getNowMillis()
    }

    return ExamModel(teacher, lesson, dateMillis)
}

fun ZalikItemDto.toModel(): ZalikModel =
    ZalikModel(lesson)

fun ExamsModel.toDto(): ExamsDto =
    ExamsDto(
        exams = exams.map { it.toDto() },
        zalik = zalik.map { it.toDto() },
    )

fun ExamModel.toDto(): ExamItemDto =
    ExamItemDto(
        teacher = teacher,
        lesson = lesson,
        date = dateMillis.toDate().format(DateTimeConstants.Format.dayDotMonthDotYear),
    )

fun ZalikModel.toDto(): ZalikItemDto =
    ZalikItemDto(lesson)