package com.therxmv.napoleon.data.repository.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val name: String,
    val year: String,
    val facultyPath: String,
    val facultyName: String,
    val specialtyName: String,
) {
    val isFmi: Boolean
        get() = facultyPath == "fmi"

    val isPpf: Boolean
        get() = facultyPath == "ppf"

    val isFipmv: Boolean
        get() = facultyPath == "fipmv"
}