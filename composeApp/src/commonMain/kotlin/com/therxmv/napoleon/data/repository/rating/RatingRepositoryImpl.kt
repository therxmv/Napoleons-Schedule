package com.therxmv.napoleon.data.repository.rating

import com.therxmv.napoleon.data.repository.rating.model.RatingModel
import com.therxmv.napoleon.data.repository.rating.model.SubjectModel
import com.therxmv.napoleon.data.repository.rating.model.ValidValueModel
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

class RatingRepositoryImpl(
    private val dataStoreSource: DataStoreSource,
) : RatingRepository { // TODO translate

    companion object {
        private const val MIN_CREDITS = 1

        private const val MIN_SCORE = 60
        private const val MAX_SCORE = 100

        private const val MIN_PROBABILITY_VALUE = 0
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

    override fun validateProbabilityInput(value: String): ValidValueModel {
        val intScore = value.toIntOrNull() ?: return ValidValueModel(value, "Score must be a number")

        if (intScore < MIN_PROBABILITY_VALUE) {
            return ValidValueModel(value, "Score must be at least $MIN_PROBABILITY_VALUE")
        }

        return ValidValueModel(value)
    }

    override fun calculateProbability(rating: Double, capacity: String, quota: String, average: String, deviation: String): Double {
        try {
            val groupSize = capacity.toInt()
            val grantQuota = quota.toInt()
            val averageRating = average.toDouble()
            val ratingDeviation = deviation.toDouble()

            // На скільки стандартних відхилень рейтинг вищий або нижчий за середній
            val z = (rating - averageRating) / ratingDeviation

            // Ймовірність того, що інший студент має вищий рейтинг
            val pHigher = 1.0 - normalCdf(z)

            // Ймовірність того, що не більше ніж (grantQuota - 1) студентів мають вищий бал
            return binomialCdf(grantQuota - 1, groupSize - 1, pHigher)
        } catch (e: Exception) {
            e.printStackTrace()
            return 0.0
        }
    }

    // Функція помилки використовується для обчислення нормального розподілу
    // Це стандартна апроксимація (метод Абрамовіца–Стеґуна)
    private fun erf(x: Double): Double {
        val a1 = 0.254829592
        val a2 = -0.284496736
        val a3 = 1.421413741
        val a4 = -1.453152027
        val a5 = 1.061405429
        val p = 0.3275911

        val sign = if (x < 0) -1.0 else 1.0
        val absX = abs(x)
        val t = 1.0 / (1.0 + p * absX)
        val y = 1.0 - (((((a5 * t + a4) * t + a3) * t + a2) * t + a1) * t) * exp(-absX * absX)
        return sign * y
    }

    // Кумулятивна функція нормального розподілу Φ(x)
    private fun normalCdf(x: Double): Double =
        0.5 * (1.0 + erf(x / sqrt(2.0)))

    // Кумулятивна функція біноміального розподілу: P(X ≤ k)
    private fun binomialCdf(k: Int, n: Int, p: Double): Double {
        if (k < 0) return 0.0
        if (k >= n) return 1.0

        val q = 1.0 - p
        var prob = q.pow(n)
        var sum = prob
        for (i in 0 until k) {
            prob *= (n - i).toDouble() / (i + 1) * (p / q)
            sum += prob
        }
        return sum
    }
}