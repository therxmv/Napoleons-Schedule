package com.therxmv.napoleon.data.source.remote.napoleon.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecialtiesDto(
    @SerialName("all_years") val allYears: List<List<SpecialtyDto>>,
)

@Serializable
data class SpecialtyDto(
    @SerialName("specialty_name") val specialtyName: String,
)