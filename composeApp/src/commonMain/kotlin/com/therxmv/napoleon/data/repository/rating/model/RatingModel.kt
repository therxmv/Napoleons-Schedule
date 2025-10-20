package com.therxmv.napoleon.data.repository.rating.model

import kotlinx.serialization.Serializable

@Serializable
data class RatingModel(
    val subjects: List<SubjectModel>,
)

@Serializable
data class SubjectModel(
    val id: String,
    val name: String,
    val credits: String,
    val score: String,
    val error: String?,
)