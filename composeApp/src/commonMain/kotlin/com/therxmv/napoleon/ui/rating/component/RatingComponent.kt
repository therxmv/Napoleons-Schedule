package com.therxmv.napoleon.ui.rating.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonres.getSyncString
import com.therxmv.napoleon.data.repository.info.InfoRepository
import com.therxmv.napoleon.data.repository.rating.RatingRepository
import com.therxmv.napoleon.ui.rating.component.RatingUiData.ProbabilityInput
import com.therxmv.napoleon.ui.rating.component.RatingUiData.SubjectInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import napoleon.leonres.generated.resources.Res
import napoleon.leonres.generated.resources.rating_add_label
import napoleon.leonres.generated.resources.rating_credits_label
import napoleon.leonres.generated.resources.rating_info_link_text
import napoleon.leonres.generated.resources.rating_info_text
import napoleon.leonres.generated.resources.rating_label
import napoleon.leonres.generated.resources.rating_name_label
import napoleon.leonres.generated.resources.rating_probability
import napoleon.leonres.generated.resources.rating_score_label
import kotlin.math.round

@Stable
class RatingComponent(
    componentContext: ComponentContext,
    private val ratingRepository: RatingRepository,
    private val infoRepository: InfoRepository,
    private val ioDispatcher: CoroutineDispatcher,
) : ComponentContext by componentContext {
    val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow(createInitialData())
    val uiState = _uiState.asStateFlow()

    init {
        observeAndSaveRating()
    }

    fun onEvent(event: RatingUiEvent) {
        when (event) {
            RatingUiEvent.AddSubjectInput -> addNewSubject()

            is RatingUiEvent.DeleteSubjectInput -> filterOutSubject(event.id)

            is RatingUiEvent.UpdateSubjectInput -> updateSubjectState(event)

            is RatingUiEvent.UpdateProbabilityInput -> updateProbabilityState(event)
        }
    }

    private fun observeAndSaveRating() {
        scope.launch(ioDispatcher) {
            _uiState.collect {
                ratingRepository.saveRating(it.toModel())
            }
        }
    }

    private fun addNewSubject() {
        _uiState.update { data ->
            val newSubjects = listOf(SubjectInput()) + data.subjectInputs

            val (number, rating) = calculateRating(newSubjects)
            val probability = calculateProbability(number, data.probabilityInputs)

            data.copy(
                subjectInputs = newSubjects,
                ratingResult = rating,
                probabilityResult = probability,
            )
        }
    }

    private fun filterOutSubject(id: String) {
        _uiState.update { data ->
            val newSubjects = data.subjectInputs.filter { it.id != id }

            val (number, rating) = calculateRating(newSubjects)
            val probability = calculateProbability(number, data.probabilityInputs)

            data.copy(
                subjectInputs = newSubjects,
                ratingResult = rating,
                probabilityResult = probability,
            )
        }
    }

    private fun updateSubjectState(event: RatingUiEvent.UpdateSubjectInput) {
        _uiState.update { data ->
            val newSubjects = data.subjectInputs.map { subject ->
                if (subject.id == event.id) {
                    val (credits, creditsError) = ratingRepository.validateCredits(event.credits ?: subject.credits)
                    val (score, scoreError) = ratingRepository.validateScore(event.score ?: subject.score)

                    subject.copy(
                        name = event.name ?: subject.name,
                        credits = credits,
                        score = score,
                        error = combineErrors(creditsError, scoreError),
                    )
                } else subject
            }

            val (number, rating) = calculateRating(newSubjects)
            val probability = calculateProbability(number, data.probabilityInputs)

            data.copy(
                subjectInputs = newSubjects,
                ratingResult = rating,
                probabilityResult = probability,
            )
        }
    }

    private fun updateProbabilityState(event: RatingUiEvent.UpdateProbabilityInput) {
        _uiState.update { data ->
            val newInputs = data.probabilityInputs.map { input ->
                if (input.id == event.id) {
                    val (value, error) = ratingRepository.validateProbabilityInput(event.value)

                    input.copy(
                        value = value,
                        error = error,
                    )
                } else input
            }

            val (number, rating) = calculateRating(data.subjectInputs)
            val probability = calculateProbability(number, newInputs)

            data.copy(
                ratingResult = rating,
                probabilityInputs = newInputs,
                probabilityResult = probability,
            )
        }
    }

    private fun calculateRating(list: List<SubjectInput>): Pair<Double, String> {
        val rating = ratingRepository.calculateRating(list.toModel())
        val rounded = round(rating * 100) / 100

        return rounded to getSyncString(Res.string.rating_label, "$rounded")
    }

    private fun calculateProbability(rating: Double, list: List<ProbabilityInput>): String {
        val capacity = list.first { it.id == ProbabilityInput.Id.Capacity }.value
        val quota = list.first { it.id == ProbabilityInput.Id.Quota }.value
        val average = list.first { it.id == ProbabilityInput.Id.Average }.value
        val deviation = list.first { it.id == ProbabilityInput.Id.Deviation }.value

        val rating = ratingRepository.calculateProbability(rating, capacity, quota, average, deviation)
        val rounded = round(rating * 100).toString()
        val percentages = if (rounded.endsWith(".0")) "${rounded.dropLast(2)}%" else "$rounded%"

        // TODO p1 percentages sometimes NaN
        return getSyncString(Res.string.rating_probability, percentages)
    }

    private fun combineErrors(vararg errors: String?): String? {
        val errorList = errors.filterNotNull().toSet().ifEmpty { null }

        return errorList?.joinToString("\n")
    }

    private fun createInitialData(): RatingUiData {
        val subjectInputs = ratingRepository.getRatingSync()?.subjects?.toUi()
            ?: listOf(SubjectInput(), SubjectInput())
        val probabilityInputs = createProbabilityInputs()

        val (number, rating) = calculateRating(subjectInputs)
        val probability = calculateProbability(number, probabilityInputs)

        return RatingUiData(
            nameLabelRes = Res.string.rating_name_label,
            creditsLabelRes = Res.string.rating_credits_label,
            scoreLabelRes = Res.string.rating_score_label,
            addInputLabelRes = Res.string.rating_add_label,
            subjectInputs = subjectInputs,
            ratingResult = rating,
            probabilityInputs = probabilityInputs,
            probabilityResult = probability,
            infoData = createInfoData(),
        )
    }

    private fun createProbabilityInputs(): List<ProbabilityInput> =
        listOf(
            ProbabilityInput(
                id = ProbabilityInput.Id.Capacity,
                value = "20",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Quota,
                value = "8",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Average,
                value = "75",
            ),
            ProbabilityInput(
                id = ProbabilityInput.Id.Deviation,
                value = "5",
            ),
        )

    private fun createInfoData(): RatingUiData.Info {
        val link = infoRepository.getLinks().educationalPrograms

        return RatingUiData.Info(
            textRes = Res.string.rating_info_text,
            link = link,
            linkTextRes = Res.string.rating_info_link_text,
        )
    }
}