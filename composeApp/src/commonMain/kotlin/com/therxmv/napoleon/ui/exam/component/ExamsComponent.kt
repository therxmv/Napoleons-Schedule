package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonui.state.LeonState
import com.therxmv.leonui.state.mapReady
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.source.remote.result.Result
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Section.Companion.EXAM_ID
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Section.Companion.ZALIK_ID
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
class ExamsComponent(
    componentContext: ComponentContext,
    private val specialtyRepository: SpecialtyRepository,
    private val profileRepository: ProfileRepository,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<LeonState<ExamsUiData>>(LeonState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: ExamsUiEvent) {
        when (event) {
            is ExamsUiEvent.EditSection -> toggleSectionEditing(id = event.sectionId, isEditing = true)
            is ExamsUiEvent.SaveSection -> toggleSectionEditing(id = event.sectionId, isEditing = false)
            is ExamsUiEvent.UpdateItem -> updateItem(event)
        }
    }

    private fun toggleSectionEditing(id: String, isEditing: Boolean) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    sections = data.sections.map { section ->
                        if (section.id == id) section.copy(isEditing = isEditing) else section
                    }
                )
            }
        }
    }

    // TODO save new data locally
    private fun updateItem(event: ExamsUiEvent.UpdateItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    sections = data.sections.map { section ->
                        if (section.id != event.sectionId) return@map section

                        section.copy(
                            items = section.items.map { item ->
                                val isSameId = item.id == event.itemId
                                val name = event.newName ?: item.name

                                when {
                                    isSameId && item is ExamsUiData.Item.Exam -> item.copy(
                                        name = name,
                                        teacher = event.newTeacher ?: item.teacher
                                    )

                                    isSameId && item is ExamsUiData.Item.Zalik -> item.copy(name = name)

                                    else -> item
                                }
                            }
                        )
                    }
                )
            }
        }
    }

    private fun loadData() {
        scope.launch {
            _uiState.update { LeonState.Loading }

            val profile = profileRepository.getNotNullProfileSync()

            val result = specialtyRepository.getExams(profile)

            _uiState.update {
                when (result) {
                    is Result.Success<ExamsModel> -> {
                        LeonState.Ready(
                            data = result.data.toUiData(),
                            cacheReason = result.reason?.message,
                        )
                    }

                    // TODO show error as card and empty sections
                    is Result.Failure -> LeonState.Error(Res.string.exams_no_data)
                }
            }
        }
    }

    private fun ExamsModel.toUiData(): ExamsUiData {
        val emptyPlaceholder = listOf(ExamsUiData.Item.EmptyPlaceholder(name = Res.string.exams_no_data))

        val examData = ExamsUiData.Section(
            id = EXAM_ID,
            title = Res.string.exams_list_title,
            items = exams.mapIndexed { index, exam ->
                ExamsUiData.Item.Exam(
                    id = "${EXAM_ID}_${index}_${exam.lesson}",
                    teacher = exam.teacher,
                    name = exam.lesson,
                    date = exam.date,
                )
            }.ifEmpty { emptyPlaceholder },
        )

        val zalikData = ExamsUiData.Section(
            id = ZALIK_ID,
            title = Res.string.zalik_list_title,
            items = zalik.mapIndexed { index, zalik ->
                ExamsUiData.Item.Zalik(
                    id = "${ZALIK_ID}_${index}_${zalik.lesson}",
                    name = zalik.lesson,
                )
            }.ifEmpty { emptyPlaceholder },
        )

        return ExamsUiData(
            sections = listOf(examData, zalikData),
        )
    }
}