package com.therxmv.napoleon.data.repository.specialty.model

data class ScheduleModel(
    val lessonsByDays: Map<String, List<LessonModel>>,
)

data class LessonModel(
    val lessonId: String,
    val lessonName: String?,
    val lessonNumber: String?,
    val link: String?,
)