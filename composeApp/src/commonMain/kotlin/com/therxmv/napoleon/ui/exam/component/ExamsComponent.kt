package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.datetime.getNowDate
import com.therxmv.datetime.toDate
import com.therxmv.leonui.state.LeonState
import com.therxmv.leonui.state.mapReady
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.info.InfoRepository
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.source.remote.result.Result
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Item
import com.therxmv.napoleon.ui.exam.component.ExamsUiData.Section
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
            is ExamsUiEvent.EditItem -> toggleItemEditing(
                sectionId = event.sectionId,
                itemId = event.itemId,
                isEditing = true,
            )

            is ExamsUiEvent.SaveItem -> toggleItemEditing(
                sectionId = event.sectionId,
                itemId = event.itemId,
                isEditing = false,
            )

            is ExamsUiEvent.UpdateItem -> updateItem(event)
            is ExamsUiEvent.DeleteItem -> deleteItem(event)
            is ExamsUiEvent.AddNewItem -> addNewItem(event)

            is ExamsUiEvent.ChangeItemDate -> openDatePicker(event)
        }
    }

    private fun openDatePicker(event: ExamsUiEvent.ChangeItemDate) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.copy(
                    datePickerData = ExamsUiData.DatePicker(
                        sectionId = event.sectionId,
                        itemId = event.itemId,
                        date = event.date,
                    ),
                )
            }
        }
    }

    private fun toggleItemEditing(sectionId: Section.Id, itemId: String, isEditing: Boolean) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.mapSectionItemsById(sectionId, itemId) { item ->
                    when (item) {
                        is Item.Editable -> item.toggleEdit(isEditing)

                        else -> item
                    }
                }
            }
        }
    }

    // TODO save new data locally
    private fun updateItem(event: ExamsUiEvent.UpdateItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.mapSectionItemsById(event.sectionId, event.itemId) { item ->
                    when (item) {
                        is Item.Editable.Exam -> item.copy(
                            name = event.newName ?: item.name,
                            teacher = event.newTeacher ?: item.teacher,
                        )

                        is Item.Editable.Zalik -> item.copy(
                            name = event.newName ?: item.name,
                        )

                        else -> item
                    }
                }
            }
        }
    }

    // TODO save new data locally
    private fun deleteItem(event: ExamsUiEvent.DeleteItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.mapSectionById(event.sectionId) { section ->
                    val withoutDeleted = section.items
                        .filter { it.id != event.itemId }
                        .orEmptyPlaceholder()

                    section.copy(items = withoutDeleted)
                }
            }
        }
    }

    // TODO save new data locally
    private fun addNewItem(event: ExamsUiEvent.AddNewItem) {
        _uiState.update { state ->
            state.mapReady { data ->
                data.mapSectionById(event.sectionId) { section ->
                    val withoutPlaceholder = section.items.filterNot { it is Item.EmptyPlaceholder }

                    val newItem = createNewItem(sectionId = section.id)

                    section.copy(items = listOf(newItem) + withoutPlaceholder)
                }
            }
        }
    }

    private fun createNewItem(sectionId: Section.Id): Item =
        when (sectionId) {
            Section.Id.Exam -> Item.Editable.Exam(
                name = Res.string.exams_default_exam_name,
                teacher = Res.string.exams_default_exam_teacher,
                date = getNowDate(), // TODO calculate today when implement date picker
                isEditing = true,
            )

            Section.Id.Zalik -> Item.Editable.Zalik(
                name = Res.string.exams_default_zalik_name,
                isEditing = true,
            )
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
        val examData = Section(
            id = Section.Id.Exam,
            title = Res.string.exams_list_title,
            items = exams.sortedBy { it.dateMillis }.map {
                Item.Editable.Exam(
                    teacher = it.teacher,
                    name = it.lesson,
                    date = it.dateMillis.toDate(),
                )
            }.orEmptyPlaceholder(),
        )

        val zalikData = Section(
            id = Section.Id.Zalik,
            title = Res.string.zalik_list_title,
            items = zalik.map { Item.Editable.Zalik(name = it.lesson) }.orEmptyPlaceholder(),
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

    private fun List<Item>.orEmptyPlaceholder(): List<Item> =
        ifEmpty { listOf(Item.EmptyPlaceholder(name = Res.string.exams_empty_placeholder)) }

    private inline fun ExamsUiData.mapSectionById(
        sectionId: Section.Id,
        crossinline transform: (Section) -> Section,
    ): ExamsUiData =
        copy(
            sections = sections.map { section ->
                if (section.id != sectionId) return@map section

                transform(section)
            },
            datePickerData = null,
        )

    private inline fun ExamsUiData.mapSectionItemsById(
        sectionId: Section.Id,
        itemId: String,
        crossinline transform: (Item) -> Item,
    ): ExamsUiData =
        mapSectionById(sectionId) { section ->
            section.copy(
                items = section.items.map { item ->
                    if (item.id != itemId) return@map item

                    transform(item)
                },
            )
        }
}