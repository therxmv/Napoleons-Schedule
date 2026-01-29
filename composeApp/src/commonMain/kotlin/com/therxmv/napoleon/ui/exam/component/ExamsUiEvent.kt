package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Section

@Stable
sealed interface ExamsUiEvent {

    data class EditItem(
        val sectionId: Section.Id,
        val itemId: String,
    ) : ExamsUiEvent

    data class SaveItem(
        val sectionId: Section.Id,
        val itemId: String,
    ) : ExamsUiEvent

    data class UpdateItem(
        val sectionId: Section.Id,
        val itemId: String,
        val newName: String? = null,
        val newTeacher: String? = null,
    ) : ExamsUiEvent

    data class DeleteItem(
        val sectionId: Section.Id,
        val itemId: String,
    ) : ExamsUiEvent

    data class AddNewItem(val sectionId: Section.Id) : ExamsUiEvent
}
