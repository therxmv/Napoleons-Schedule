package com.therxmv.napoleon.ui.schedule.component

import com.therxmv.datetime.getNowDateTime
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.specialty.model.LessonModel
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ScheduleUiConverter(
    private val defaultDispatcher: CoroutineDispatcher,
) {

    companion object {
        private const val END_OF_DAY = 17
    }

    suspend fun modelToUiData(
        model: ScheduleModel,
        openLessonUrl: (String) -> Unit,
    ): ScheduleUiData =
        withContext(defaultDispatcher) {
            val selectedKey = model.lessonsByDays.findSelectedKey()

            ScheduleUiData(
                days = model.lessonsByDays.map {
                    val lessons = it.value

                    if (lessons.isEmpty()) {
                        ScheduleUiData.Day.Empty(
                            name = "${it.key} - ${Res.string.schedule_no_lessons}",
                        )
                    } else {
                        ScheduleUiData.Day.Default(
                            name = it.key,
                            lessons = lessons.toUiData(openLessonUrl),
                            isExpanded = it.key == selectedKey,
                            expandEvent = ScheduleUiEvent.ExpandDay(it.key),
                        )
                    }
                }
            )
        }

    private fun List<LessonModel>.toUiData(
        openLessonUrl: (String) -> Unit,
    ): List<ScheduleUiData.Lesson> =
        map {
            val (id, name, number, link) = it

            when {
                name.isNullOrBlank() -> ScheduleUiData.Lesson.Empty(
                    id = id,
                    number = number,
                )

                number.isNullOrBlank() -> ScheduleUiData.Lesson.ByTime(
                    id = id,
                    name = name,
                    time = link,
                )

                link != null && link.isValidLink() -> ScheduleUiData.Lesson.Online(
                    id = id,
                    name = name,
                    number = number,
                    link = link,
                    onClick = { openLessonUrl(link) },
                )

                else -> ScheduleUiData.Lesson.Offline(
                    id = id,
                    name = name,
                    number = number,
                    classroom = link,
                )
            }
        }

    private fun Map<String, List<LessonModel>>.findSelectedKey(): String? {
        val date = getNowDateTime()
        val todayIndex = date.dayOfWeek.ordinal
        val indexByHour = todayIndex.takeIf { date.hour < END_OF_DAY } ?: (todayIndex + 1)

        val firstNotEmptyKey: Iterable<Map.Entry<String, List<*>>>.() -> String? = {
            find { it.value.isNotEmpty() }?.key
        }
        val entryList = this.entries.toList()

        return when {
            indexByHour >= size -> entryList.firstNotEmptyKey()

            indexByHour < size -> {
                val entry = entryList[indexByHour]

                entry.key.takeIf { entry.value.isNotEmpty() }
                // "+ entryList" in case slice returns only Friday, so it could take Monday or next day
                    ?: (entryList.slice(indexByHour..entryList.lastIndex) + entryList).firstNotEmptyKey()
            }

            else -> null
        }
    }

    private fun String.isValidLink() = matches("[A-Za-z].*://\\S*".toRegex())
}