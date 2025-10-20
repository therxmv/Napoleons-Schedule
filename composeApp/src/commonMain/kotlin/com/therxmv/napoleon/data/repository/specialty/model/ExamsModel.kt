package com.therxmv.napoleon.data.repository.specialty.model

data class ExamsModel(
    val exams: List<ExamModel>,
    val zalik: List<ZalikModel>,
)

data class ExamModel(
    val teacher: String,
    val lesson: String,
    val date: String,
)

data class ZalikModel(
    val lesson: String,
)