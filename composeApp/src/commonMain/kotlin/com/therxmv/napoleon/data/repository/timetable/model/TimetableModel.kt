package com.therxmv.napoleon.data.repository.timetable.model

data class TimetableModel(
    val firstShift: ShiftModel,
    val secondShift: ShiftModel,
)

data class ShiftModel(
    val time: List<String>,
)