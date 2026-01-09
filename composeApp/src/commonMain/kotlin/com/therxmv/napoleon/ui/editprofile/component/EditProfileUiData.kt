package com.therxmv.napoleon.ui.editprofile.component

import androidx.compose.runtime.Immutable
import com.therxmv.leonui.input.LeonDropdownInputData

@Immutable
data class EditProfileUiData(
    val facultyDropdown: LeonDropdownInputData,
    val yearDropdown: LeonDropdownInputData,
    val specialtyDropdown: LeonDropdownInputData,
    val saveLabel: String,
) {
    val isAllSelected: Boolean
        get() = facultyDropdown.value != null && yearDropdown.value != null && specialtyDropdown.value != null
}