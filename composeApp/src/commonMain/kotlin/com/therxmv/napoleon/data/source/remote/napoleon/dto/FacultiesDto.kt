package com.therxmv.napoleon.data.source.remote.napoleon.dto

import com.therxmv.napoleon.data.repository.faculty.model.FacultiesModel
import com.therxmv.napoleon.data.repository.faculty.model.FacultyModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacultiesDto(
    @SerialName("all_faculties") val allFaculties: List<FacultyDto>,
)

@Serializable
data class FacultyDto(
    @SerialName("faculty_name") val facultyName: String,
    @SerialName("folder_name") val folderName: String,
)

fun FacultiesDto.toModel(): FacultiesModel =
    FacultiesModel(allFaculties.map { it.toModel() })

fun FacultyDto.toModel(): FacultyModel =
    FacultyModel(facultyName, folderName)