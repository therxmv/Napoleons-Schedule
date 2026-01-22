package com.therxmv.napoleon.ui.exam.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonui.state.LeonState
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.model.ExamsModel
import com.therxmv.napoleon.data.source.remote.result.Result
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

                    is Result.Failure -> LeonState.Error(Res.string.exams_no_data)
                }
            }
        }
    }

    private fun ExamsModel.toUiData(): ExamsUiData {
        val emptyPlaceholder = listOf(ExamsUiData.Item.EmptyPlaceholder(Res.string.exams_no_data))
        val examData = ExamsUiData.ItemsData(
            title = Res.string.exams_list_title,
            items = exams.map {
                ExamsUiData.Item.Exam(
                    teacher = it.teacher,
                    name = it.lesson,
                    date = it.date,
                )
            }.ifEmpty { emptyPlaceholder },
        )

        val zalikData = ExamsUiData.ItemsData(
            title = Res.string.zalik_list_title,
            items = zalik.map {
                ExamsUiData.Item.Zalik(
                    name = it.lesson,
                )
            }.ifEmpty { emptyPlaceholder },
        )

        return ExamsUiData(
            items = listOf(examData, zalikData),
        )
    }
}