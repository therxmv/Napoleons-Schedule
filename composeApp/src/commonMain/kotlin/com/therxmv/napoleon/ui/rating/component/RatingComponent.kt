package com.therxmv.napoleon.ui.rating.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.napoleon.data.repository.rating.RatingRepository
import com.therxmv.napoleon.ui.rating.component.RatingUiState.Subject
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.round

class RatingComponent(
    componentContext: ComponentContext,
    private val ratingRepository: RatingRepository,
) : ComponentContext by componentContext {
    val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow(createInitialData())
    val uiState = _uiState.asStateFlow()

    init {
        observeAndSaveRating()
    }

    fun onEvent(event: RatingUiEvent) {
        when (event) {
            RatingUiEvent.AddInput -> addNewInput()

            is RatingUiEvent.DeleteInput -> filterOutInput(event.id)

            is RatingUiEvent.UpdateInput -> updateInputState(event)
        }
    }

    private fun observeAndSaveRating() {
        scope.launch {
            _uiState.collect {
                ratingRepository.saveRating(it.toModel())
            }
        }
    }

    private fun addNewInput() {
        _uiState.update { data ->
            val newSubjects = listOf(Subject()) + data.subjects
            val result = calculateRating(newSubjects)

            data.copy(
                subjects = newSubjects,
                result = result
            )
        }
    }

    private fun filterOutInput(id: String) {
        _uiState.update { data ->
            val newSubjects = data.subjects.filter { it.id != id }
            val result = calculateRating(newSubjects)

            data.copy(
                subjects = newSubjects,
                result = result,
            )
        }
    }

    private fun updateInputState(event: RatingUiEvent.UpdateInput) {
        _uiState.update { data ->
            val newSubjects = data.subjects.map { subject ->
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

            val result = calculateRating(newSubjects)

            data.copy(
                subjects = newSubjects,
                result = result,
            )
        }
    }

    private fun calculateRating(list: List<Subject>): String {
        val rating = ratingRepository.calculateRating(list.toModel())
        val rounded = round(rating * 100) / 100

        return "Ваш рейтинг: $rounded" // TODO translate
    }

    private fun combineErrors(vararg errors: String?): String? {
        val errorList = errors.filterNotNull().toSet().ifEmpty { null }

        return errorList?.joinToString("\n")
    }

    private fun createInitialData(): RatingUiState {
        val subjects = ratingRepository.getRatingSync()?.subjects?.toUi()
            ?: listOf(Subject(), Subject())

        val result = calculateRating(subjects)

        return RatingUiState( // TODO translate
            nameLabel = "Предмет",
            creditsLabel = "Кредити",
            scoreLabel = "Бал",
            addInputLabel = "+ Add Subject",
            subjects = subjects,
            result = result,
        )
    }
}