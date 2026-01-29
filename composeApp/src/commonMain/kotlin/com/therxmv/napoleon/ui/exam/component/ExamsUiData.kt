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
        val id: Id,
        val title: String,
        val items: List<Item>,
    ) {
        override fun toString(): String =
            "$title:\n${items.joinToString("\n") { it.toString() }}"

        enum class Id { Exam, Zalik }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Stable
    sealed interface Item {

        val id: String
        val name: String

        @Stable
        sealed interface Editable : Item {
            val isEditing: Boolean
            fun toggleEdit(isEditing: Boolean): Item

            data class Exam(
                override val id: String = Uuid.random().toHexDashString(),
                override val name: String,
                override val isEditing: Boolean = false,
                val teacher: String,
                val date: String,
            ) : Editable {
                override fun toggleEdit(isEditing: Boolean): Item = copy(isEditing = isEditing)

                override fun toString(): String =
                    "$date - $name, $teacher"
            }

            data class Zalik(
                override val id: String = Uuid.random().toHexDashString(),
                override val name: String,
                override val isEditing: Boolean = false,
            ) : Editable {
                override fun toggleEdit(isEditing: Boolean): Item = copy(isEditing = isEditing)

                override fun toString(): String = name
            }
        }

        data class EmptyPlaceholder(
            override val id: String = Uuid.random().toHexDashString(),
            override val name: String,
        ) : Item {
            override fun toString(): String = name
        }
    }
}
