package com.therxmv.napoleon.ui.schedule.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.data.repository.analytics.AnalyticsEvents
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.specialty.SpecialtyRepository
import com.therxmv.napoleon.data.repository.specialty.model.ScheduleModel
import com.therxmv.napoleon.data.source.remote.result.Result
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
class ScheduleComponent(
    componentContext: ComponentContext,
    private val specialtyRepository: SpecialtyRepository,
    private val profileRepository: ProfileRepository,
    private val analyticsRepository: AnalyticsRepository,
    private val scheduleUiConverter: ScheduleUiConverter,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<BaseState<ScheduleUiData>>(BaseState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<ScheduleUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _uiState.update { BaseState.Loading }

            val profile = profileRepository.getNotNullProfileSync()

            val result = specialtyRepository.getSchedule(profile)

            _uiState.update {
                when (result) {
                    is Result.Success<ScheduleModel> -> {
                        BaseState.Ready(
                            data = scheduleUiConverter.modelToUiData(result.data, ::openLessonUrl),
                            cacheReason = result.reason?.message,
                        )
                    }

                    is Result.Failure -> BaseState.Error(result.reason.message, ::loadData)
                }
            }

            analyticsRepository.reportScheduleOpened(profile.facultyName, profile.specialtyName)
        }
    }

    fun onEvent(event: ScheduleUiEvent) {
        when (event) {
            is ScheduleUiEvent.ExpandDay -> expandDay(event.name)

            ScheduleUiEvent.CopyDay -> analyticsRepository.reportEvent(AnalyticsEvents.COPY_SCHEDULE)

            ScheduleUiEvent.CopyLessonLink -> analyticsRepository.reportEvent(AnalyticsEvents.COPY_ONLINE_LINK)
        }
    }

    private fun openLessonUrl(url: String) {
        scope.launch {
            _uiEffect.send(ScheduleUiEffect.OpenWebUrl(url))
        }
        analyticsRepository.reportEvent(AnalyticsEvents.OPEN_ONLINE_LESSON)
    }

    private fun expandDay(name: String) {
        _uiState.updateReady { data ->
            val mutableDays = data.days.toMutableList()

            val dayIndex = mutableDays.indexOfFirst { it.name == name }

            (mutableDays.getOrNull(dayIndex) as? ScheduleUiData.Day.Default)?.let { day ->
                val newDay = day.copy(isExpanded = day.isExpanded.not())
                mutableDays[dayIndex] = newDay
            }

            data.copy(
                days = mutableDays,
            )
        }
    }

    private fun MutableStateFlow<BaseState<ScheduleUiData>>.updateReady(dataCreator: (ScheduleUiData) -> ScheduleUiData) {
        update { state ->
            val state = (state as? BaseState.Ready<ScheduleUiData>) ?: return@update state

            state.copy(
                data = dataCreator(state.data)
            )
        }
    }
}