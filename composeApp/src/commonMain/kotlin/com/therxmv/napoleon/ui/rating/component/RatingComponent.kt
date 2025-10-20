package com.therxmv.napoleon.ui.rating.component

import com.arkivanov.decompose.ComponentContext
import com.therxmv.napoleon.ui.rating.component.RatingUiState.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RatingComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val _uiState = MutableStateFlow(createInitialData())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RatingUiEvent) {
        when (event) {
            RatingUiEvent.AddInput -> addNewInput()

            is RatingUiEvent.DeleteInput -> filterOutInput(event.id)

            is RatingUiEvent.UpdateInput -> updateInputState(event)
        }
    }

    private fun addNewInput() {
        _uiState.update { data ->
            data.copy(subjects = listOf(Subject()) + data.subjects)
        }
    }

    private fun filterOutInput(id: String) {
        _uiState.update { data ->
            data.copy(subjects = data.subjects.filter { it.id != id })
        }
    }

    private fun updateInputState(event: RatingUiEvent.UpdateInput) {
        _uiState.update { data ->
            val newInputs = data.subjects.map { input ->
                if (input.id == event.id) {
                    val newCredits = event.credits
                    val newScore = event.score

                    input.copy(
                        name = event.name ?: input.name,
                        credits = newCredits ?: input.credits,
                        score = newScore ?: input.score,
                    )
                } else input
            }

            data.copy(subjects = newInputs)
        }

        calculateRating()
    }

    private fun calculateRating() {

    }

    private fun createInitialData(): RatingUiState =
        RatingUiState( // TODO translate
            nameLabel = "Предмет",
            creditsLabel = "Кредити",
            scoreLabel = "Бал",
            addInputLabel = "+ Add Subject",
            subjects = emptyList(),
            result = "Ваш рейтинг: 0.0",
        )
}