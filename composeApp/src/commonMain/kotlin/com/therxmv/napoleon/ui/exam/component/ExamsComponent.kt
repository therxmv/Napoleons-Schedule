package com.therxmv.napoleon.ui.exam.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.data.repository.model.ExamsModel
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.source.remote.result.Result
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExamsComponent(
    componentContext: ComponentContext,
    private val specialtyRepository: SpecialtyRepository,
    private val profileRepository: ProfileRepository,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<BaseState<ExamsUiData>>(BaseState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _uiState.update { BaseState.Loading }

            val profile = profileRepository.getNotNullProfileSync()

            val result = specialtyRepository.getExams(profile)

            _uiState.update {
                when (result) {
                    is Result.Success<ExamsModel> -> {
                        BaseState.Ready(
                            data = result.data.toUiData(),
                            cacheReason = result.reason?.message,
                        )
                    }

                    is Result.Failure -> BaseState.Error(Res.string.exams_no_data)
                }
            }
        }
    }

    private fun ExamsModel.toUiData(): ExamsUiData {
        val emptyPlaceholder = ExamsUiData.Item.EmptyPlaceholder(Res.string.exams_no_data)
        val exams = exams.map {
            ExamsUiData.Item.Exam(
                teacher = it.teacher,
                lesson = it.lesson,
                date = it.date,
            )
        }.ifEmpty { listOf(emptyPlaceholder) }

        val zalik = zalik.map {
            ExamsUiData.Item.Zalik(
                lesson = it.lesson,
            )
        }.ifEmpty { listOf(emptyPlaceholder) }

        return ExamsUiData(
            items = buildList {
                ExamsUiData.Item.Title(Res.string.exams_list_title).also { add(it) }
                addAll(exams)

                ExamsUiData.Item.Title(Res.string.zalik_list_title).also { add(it) }
                addAll(zalik)
            }
        )
    }
}