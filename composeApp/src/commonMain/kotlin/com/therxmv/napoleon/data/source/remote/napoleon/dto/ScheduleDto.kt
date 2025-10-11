package com.therxmv.napoleon.data.source.remote.napoleon.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("week") val week: List<List<LessonDto>>,
)

@Serializable
data class LessonDto(
    @SerialName("lessonId") val lessonId: String? = null,
    @SerialName("lesson_name") val lessonName: String? = null,
    @SerialName("lesson_number") val lessonNumber: String? = null,
    @SerialName("link") val link: String? = null,
)