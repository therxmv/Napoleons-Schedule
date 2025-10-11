package com.therxmv.napoleon.data.repository.model

data class RatingModel(
    val list: List<RatingItemModel>,
)

data class RatingItemModel(
    val name: String,
    val credits: Int,
)