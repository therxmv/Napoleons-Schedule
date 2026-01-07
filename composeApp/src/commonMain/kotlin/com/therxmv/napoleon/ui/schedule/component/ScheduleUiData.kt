package com.therxmv.napoleon.ui.schedule.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.therxmv.napoleon.Res

@Immutable
data class ScheduleUiData(
    val days: List<Day>,
) {

    @Stable
    sealed interface Day {

        val name: String

        @Immutable
        data class Default(
            override val name: String,
            val lessons: List<Lesson>,
            val isExpanded: Boolean,
            val expandEvent: ScheduleUiEvent.ExpandDay,
        ) : Day {
            override fun toString(): String =
                "$name\n${lessons.joinToString("\n") { it.toString() }}"
        }

        data class Empty(
            override val name: String,
        ) : Day
    }

    @Stable
    sealed interface Lesson {

        val id: String
        val name: String

        /**
         * Has valid link for meeting
         */
        data class Online(
            override val id: String,
            override val name: String,
            val number: String,
            val link: String,
            val onClick: () -> Unit,
        ) : Lesson {
            override fun toString(): String =
                "$number) $name $link"
        }

        /**
         * Doesn't have link and might have classroom
         */
        data class Offline(
            override val id: String,
            override val name: String,
            val number: String,
            val classroom: String?,
        ) : Lesson {
            override fun toString(): String =
                "$number) $name ${classroom.orEmpty()}"
        }

        /**
         * Used for PPF
         */
        data class ByTime(
            override val id: String,
            override val name: String,
            val time: String?,
        ) : Lesson {
            override fun toString(): String =
                "${time.orEmpty()} $name"
        }

        data class Empty(
            override val id: String,
            val number: String?,
            override val name: String = Res.string.schedule_no_lesson,
        ) : Lesson {
            override fun toString(): String =
                "$number) $name"
        }
    }
}