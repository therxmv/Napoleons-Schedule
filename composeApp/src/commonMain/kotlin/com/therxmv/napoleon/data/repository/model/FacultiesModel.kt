package com.therxmv.napoleon.data.repository.model

data class FacultiesModel(
    val faculties: List<FacultyModel>,
)

data class FacultyModel(
    val facultyName: String,
    val folderName: String,
)