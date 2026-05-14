package com.therxmv.napoleon.data.repository.converter

import com.therxmv.leonres.getSyncString
import com.therxmv.napoleon.data.repository.specialty.model.LessonModel
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import com.therxmv.napoleon.data.source.remote.napoleon.dto.LessonDto
import com.therxmv.napoleon.data.source.remote.napoleon.dto.ScheduleDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.schedule_friday
import napoleon.leonres.generated.resources.schedule_monday
import napoleon.leonres.generated.resources.schedule_saturday
import napoleon.leonres.generated.resources.schedule_sunday
import napoleon.leonres.generated.resources.schedule_thursday
import napoleon.leonres.generated.resources.schedule_tuesday
import napoleon.leonres.generated.resources.schedule_unknown_day
import napoleon.leonres.generated.resources.schedule_wednesday

class ScheduleConverter(
    private val defaultDispatcher: CoroutineDispatcher,
) {

    suspend fun dtoToModel(dto: ScheduleDto): ScheduleModel =
        withContext(defaultDispatcher) {
            ScheduleModel(
                lessonsByDays = dto.week.mapIndexed { index, list ->
                    val dayOfWeek = index.toDayOfWeek()
                    val models = list.distinct().mapToModelWithGroups(dayOfWeek)

                    (dayOfWeek to models)
                }.toMap()
            )
        }

    private fun Int.toDayOfWeek(): String =
        when (this) {
            0 -> Res.string.schedule_monday
            1 -> Res.string.schedule_tuesday
            2 -> Res.string.schedule_wednesday
            3 -> Res.string.schedule_thursday
            4 -> Res.string.schedule_friday
            5 -> Res.string.schedule_saturday
            6 -> Res.string.schedule_sunday
            else -> Res.string.schedule_unknown_day
        }.let(::getSyncString)

    private fun List<LessonDto>.mapToModelWithGroups(day: String): List<LessonModel> =
        groupBy { it.lessonNumber }.values.map { group ->
            group.mapIndexed { index, item ->
                val lesson = LessonModel(
                    lessonId = item.createId(index, day),
                    lessonName = item.lessonName,
                    lessonNumber = item.lessonNumber,
                    link = item.link,
                )

                if (group.size > 1 && lesson.lessonNumber.isNullOrBlank().not()) {
                    lesson.copy(lessonNumber = "${lesson.lessonNumber}.${index + 1}")
                } else {
                    lesson
                }
            }
        }.flatten()

    private fun LessonDto.createId(index: Int, day: String): String {
        val number = lessonNumber ?: (index + 1)
        return "$index-$day-$number-$lessonName"
    }
}