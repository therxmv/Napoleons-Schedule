package com.therxmv.napoleon.data.repository.rating

import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.data.repository.rating.model.ValidValueModel

interface RatingRepository {

    suspend fun saveRating(model: RatingModel)

    fun getRatingSync(): RatingModel?

    fun validateCredits(value: String): ValidValueModel

    fun validateScore(value: String): ValidValueModel

    fun calculateRating(list: List<SubjectModel>): Double

    fun calculateProbability(rating: Double, capacity: String, quota: String, average: String, deviation: String): Double

    fun validateProbabilityInput(value: String): ValidValueModel
}