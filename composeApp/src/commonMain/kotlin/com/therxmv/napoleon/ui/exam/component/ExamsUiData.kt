package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class ExamsUiData(
    val items: List<ItemsData>,
) {

    @Immutable
    data class ItemsData(
        val title: String,
        val items: List<Item>,
    ) {
        override fun toString(): String =
            "$title:\n${items.joinToString("\n") { it.toString() }}"
    }

    @Stable
    sealed interface Item {

        data class EmptyPlaceholder(val text: String) : Item {
            override fun toString(): String = text
        }

        data class Exam(
            val teacher: String,
            val name: String,
            val date: String,
        ) : Item {
            override fun toString(): String =
                "$date - $name, $teacher"
        }

        data class Zalik(
            val name: String,
        ) : Item {
            override fun toString(): String = name
        }
    }
}