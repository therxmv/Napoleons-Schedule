package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonui.state.LeonState
import com.therxmv.leonui.state.mapReady
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.info.InfoRepository
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
    private val infoRepository: InfoRepository,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<LeonState<ExamsUiData>>(LeonState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: ExamsUiEvent) {
        when (event) {
            is ExamsUiEvent.EditSection -> toggleSectionEditing(
                id = event.sectionId,
                isEditing = true,
            )

            is ExamsUiEvent.SaveSection -> toggleSectionEditing(
                id = event.sectionId,
                isEditing = false,
            )

            is ExamsUiEvent.UpdateItem -> updateItem(event)
            is ExamsUiEvent.DeleteItem -> deleteItem(event)
            is ExamsUiEvent.AddNewItem -> addNewItem(event)
        }
    }

    private fun toggleSectionEditing(id: String, isEditing: Boolean) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    sections = data.sections.map { section ->
                        if (section.id == id) {
                            val newItems = if (isEditing) {
                                section.items.andAddNew()
                            } else {
                                section.items.orEmptyPlaceholder()
                            }
                            section.copy(isEditing = isEditing, items = newItems)
                        } else {
                            section
                        }
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
                                        teacher = event.newTeacher ?: item.teacher,
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

    // TODO save new data locally
    private fun deleteItem(event: ExamsUiEvent.DeleteItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    sections = data.sections.map { section ->
                        if (section.id != event.sectionId) return@map section

                        val filteredItems = section.items.filter { it.id != event.itemId }

                        section.copy(items = filteredItems)
                    }
                )
            }
        }
    }

    // TODO save new data locally
    private fun addNewItem(event: ExamsUiEvent.AddNewItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    sections = data.sections.map { section ->
                        if (section.id != event.sectionId) return@map section

                        val currentItems = section.items.filter {
                            it is ExamsUiData.Item.Exam || it is ExamsUiData.Item.Zalik
                        }
                        val addNewItem = section.items.first { it is ExamsUiData.Item.AddNew }

                        val newItem = createNewItem(sectionId = section.id)

                        section.copy(items = currentItems + newItem + addNewItem)
                    }
                )
            }
        }
    }

    private fun createNewItem(sectionId: String): ExamsUiData.Item =
        when (sectionId) {
            EXAM_ID -> ExamsUiData.Item.Exam(
                name = Res.string.exams_default_exam_name,
                teacher = Res.string.exams_default_exam_teacher,
                date = "TBD", // TODO calculate today when implement date picker
            )

            ZALIK_ID -> ExamsUiData.Item.Zalik(
                name = Res.string.exams_default_zalik_name,
            )

            else -> error("Unknown section id")
        }

    private fun loadData() {
        scope.launch {
            _uiState.update { LeonState.Loading }

            val profile = profileRepository.getNotNullProfileSync()

            val result = specialtyRepository.getExams(profile)

            _uiState.update {
                when (result) {
                    is Result.Success<ExamsModel> -> {
                        LeonState.Ready(data = result.data.toUiData())
                    }

                    is Result.Failure -> LeonState.Ready(
                        data = ExamsModel(exams = emptyList(), zalik = emptyList()).toUiData(),
                        cacheReason = Res.string.exams_no_data,
                    )
                }
            }
        }
    }

    private fun ExamsModel.toUiData(): ExamsUiData {
        val examData = ExamsUiData.Section(
            id = EXAM_ID,
            title = Res.string.exams_list_title,
            items = exams.mapIndexed { index, exam ->
                ExamsUiData.Item.Exam(
                    teacher = exam.teacher,
                    name = exam.lesson,
                    date = exam.date,
                )
            }.orEmptyPlaceholder(),
        )

        val zalikData = ExamsUiData.Section(
            id = ZALIK_ID,
            title = Res.string.zalik_list_title,
            items = zalik.mapIndexed { index, zalik ->
                ExamsUiData.Item.Zalik(
                    name = zalik.lesson,
                )
            }.orEmptyPlaceholder(),
        )

        return ExamsUiData(
            infoData = createInfoData(),
            sections = listOf(examData, zalikData),
        )
    }

    private fun createInfoData(): ExamsUiData.Info {
        val link = infoRepository.getLinks().examCalendar

        return ExamsUiData.Info(
            text = Res.string.exams_edit_info,
            link = link,
            linkText = Res.string.exams_edit_info_link_text,
        )
    }

    private fun List<ExamsUiData.Item>.orEmptyPlaceholder(): List<ExamsUiData.Item> =
        filterNot { it is ExamsUiData.Item.AddNew }.ifEmpty {
            listOf(
                ExamsUiData.Item.EmptyPlaceholder(
                    name = Res.string.exams_empty_placeholder
                )
            )
        }

    private fun List<ExamsUiData.Item>.andAddNew(): List<ExamsUiData.Item> =
        filterNot { it is ExamsUiData.Item.EmptyPlaceholder } + ExamsUiData.Item.AddNew(name = Res.string.exams_add_new)
}