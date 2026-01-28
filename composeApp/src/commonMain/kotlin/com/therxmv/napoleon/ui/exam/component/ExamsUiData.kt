package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class ExamsUiData(
    val infoData: Info,
    val sections: List<Section>,
) {

    data class Info(
        val text: String,
        val link: String,
        val linkText: String,
    )

    @Immutable
    data class Section(
        val id: String,
        val title: String,
        val items: List<Item>,
        val isEditing: Boolean = false,
    ) {
        override fun toString(): String =
            "$title:\n${items.joinToString("\n") { it.toString() }}"

        companion object {
            const val EXAM_ID = "exam"
            const val ZALIK_ID = "zalik"
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Stable
    sealed interface Item {

        val id: String
        val name: String

        data class EmptyPlaceholder(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
        ) : Item {
            override fun toString(): String = name
        }

        data class Exam(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
            val teacher: String,
            val date: String,
        ) : Item {
            override fun toString(): String =
                "$date - $name, $teacher"
        }

        data class Zalik(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
        ) : Item {
            override fun toString(): String = name
        }

        data class AddNew(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
        ) : Item {
            override fun toString(): String = ""
        }
    }
}
