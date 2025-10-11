package com.therxmv.napoleon.data.source.remote.napoleon.dto

import com.therxmv.napoleon.data.repository.model.RatingItemModel
import com.therxmv.napoleon.data.repository.model.RatingModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    @SerialName("list") val list: List<RatingItemDto>,
)

@Serializable
data class RatingItemDto(
    @SerialName("name") val name: String,
    @SerialName("credits") val credits: Int,
)

fun RatingDto.toModel(): RatingModel =
    RatingModel(list.map { it.toModel() })

fun RatingItemDto.toModel(): RatingItemModel =
    RatingItemModel(name, credits)