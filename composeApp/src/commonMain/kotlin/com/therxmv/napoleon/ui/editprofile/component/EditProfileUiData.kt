package com.therxmv.napoleon.ui.editprofile.component

import androidx.compose.runtime.Immutable
import com.therxmv.leonui.input.LeonDropdownInputData
import org.jetbrains.compose.resources.StringResource

@Immutable
data class EditProfileUiData(
    val facultyDropdown: LeonDropdownInputData,
    val yearDropdown: LeonDropdownInputData,
    val specialtyDropdown: LeonDropdownInputData,
    val saveLabelRes: StringResource,
) {
    val isAllSelected: Boolean
        get() = facultyDropdown.value != null && yearDropdown.value != null && specialtyDropdown.value != null
}