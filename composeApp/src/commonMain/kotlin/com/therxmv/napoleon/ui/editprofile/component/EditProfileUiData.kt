package com.therxmv.napoleon.ui.editprofile.component

import androidx.compose.runtime.Immutable

@Immutable
data class EditProfileUiData(
    val facultyDropdown: Dropdown,
    val yearDropdown: Dropdown,
    val specialtyDropdown: Dropdown,
    val saveLabel: String,
) {
    val isAllSelected: Boolean
        get() = facultyDropdown.value != null && yearDropdown.value != null && specialtyDropdown.value != null

    @Immutable
    data class Dropdown(
        val placeholder: String,
        val value: String? = null,
        val items: List<String> = emptyList(),
        val onClick: (String) -> Unit,
    )
}