package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable

@Stable
sealed interface ExamsUiEvent {

    data class EditItem(
        val sectionId: String,
        val itemId: String,
    ) : ExamsUiEvent

    data class SaveItem(
        val sectionId: String,
        val itemId: String,
    ) : ExamsUiEvent

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

    data class AddNewItem(val sectionId: String) : ExamsUiEvent
}
