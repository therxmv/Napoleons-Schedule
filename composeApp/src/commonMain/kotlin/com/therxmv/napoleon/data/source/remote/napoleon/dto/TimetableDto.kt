package com.therxmv.napoleon.data.source.remote.napoleon.dto

import com.therxmv.napoleon.data.repository.model.ShiftModel
import com.therxmv.napoleon.data.repository.model.TimetableModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimetableDto(
    val firstShift: ShiftDto,
    val secondShift: ShiftDto,
)

@Serializable
data class ShiftDto(
    @SerialName("time") val time: List<String>,
)

fun TimetableDto.toModel(): TimetableModel =
    TimetableModel(
        firstShift = firstShift.toModel(),
        secondShift = secondShift.toModel(),
    )

fun ShiftDto.toModel(): ShiftModel =
    ShiftModel(time)