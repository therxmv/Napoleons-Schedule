package com.therxmv.napoleon.data.source.remote.napoleon.dto

import com.therxmv.datetime.getNowDate
import com.therxmv.datetime.getNowMillis
import com.therxmv.datetime.toMillis
import com.therxmv.napoleon.data.repository.specialty.model.ExamModel
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.repository.specialty.model.ZalikModel
import kotlinx.datetime.LocalDate
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
        val year = if (month <= 2 && today.month.number >= 9) today.year + 1 else today.year
        LocalDate(year, month, day).toMillis()
    } catch (_: Exception) {
        getNowMillis()
    }

    return ExamModel(teacher, lesson, dateMillis)
}

fun ZalikItemDto.toModel(): ZalikModel =
    ZalikModel(lesson)