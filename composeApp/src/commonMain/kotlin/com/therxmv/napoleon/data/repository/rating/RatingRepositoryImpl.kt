package com.therxmv.napoleon.data.repository.rating

import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.data.repository.rating.model.ValidValueModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import kotlinx.coroutines.runBlocking

class RatingRepositoryImpl(
    private val dataStoreSource: DataStoreSource,
) : RatingRepository { // TODO translate

    companion object {
        private const val MIN_CREDITS = 1

        private const val MIN_SCORE = 60
        private const val MAX_SCORE = 100
    }

    override fun calculateRating(list: List<SubjectModel>): Double {
        try {
            val credits = list.sumOf { it.credits.toDouble() }
            val scores = list.sumOf { it.score.toDouble() * it.credits.toDouble() }

            if (credits == 0.0) return 0.0

            return 90 * (scores / (credits * 100))
        } catch (_: Exception) {
            return 0.0
        }
    }

    override suspend fun saveRating(model: RatingModel) {
        dataStoreSource.setRating(model)
    }

    override fun getRatingSync(): RatingModel? =
        runBlocking { dataStoreSource.getRating() }

    override fun validateCredits(value: String): ValidValueModel {
        val intCredits = value.toIntOrNull() ?: return ValidValueModel(value, "Credits must be a number")

        if (intCredits < MIN_CREDITS) {
            return ValidValueModel(value, "Credits must be at least $MIN_CREDITS")
        }

        return ValidValueModel(value)
    }

    override fun validateScore(value: String): ValidValueModel {
        val intScore = value.toIntOrNull() ?: return ValidValueModel(value, "Score must be a number")

        if (intScore < MIN_SCORE || intScore > MAX_SCORE) {
            return ValidValueModel(value, "Score must be at least $MIN_SCORE and at most $MAX_SCORE")
        }

        return ValidValueModel(value)
    }
}