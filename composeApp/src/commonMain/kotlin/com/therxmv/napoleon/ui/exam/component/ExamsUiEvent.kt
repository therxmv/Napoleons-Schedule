package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable

@Stable
sealed interface ExamsUiEvent {

    data class EditSection(val sectionId: String) : ExamsUiEvent

    data class SaveSection(val sectionId: String) : ExamsUiEvent

    data class UpdateItem(
        val sectionId: String,
        val itemId: String,
        val newName: String? = null,
        val newTeacher: String? = null,
    ) : ExamsUiEvent

    data class DeleteItem(
        val sectionId: String,
        val itemId: String,
    ) : ExamsUiEvent
}
