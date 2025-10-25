package com.therxmv.napoleon.data.repository.faculty.model

data class YearsModel(
    val years: List<String>,
)

data class SpecialtiesModel(
    val specialtiesByYear: Map<String, List<SpecialtyModel>>,
)

data class SpecialtyModel(
    val specialtyName: String,
)