package com.therxmv.napoleon.ui.profile.component

import org.jetbrains.compose.resources.StringResource

data class ProfileUiData(
    val infoTitleRes: StringResource,
    val facultyLabelRes: StringResource,
    val faculty: String,
    val specialtyLabelRes: StringResource,
    val specialty: String,
    val editButtonLabelRes: StringResource,
)