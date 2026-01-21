package com.therxmv.napoleon.ui.timetable.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonui.state.LeonState
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.data.repository.analytics.AnalyticsEvents
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.timetable.TimetableRepository
import com.therxmv.napoleon.data.repository.timetable.model.TimetableModel
import com.therxmv.napoleon.data.source.remote.result.Result
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
class TimetableComponent(
    componentContext: ComponentContext,
    private val dismiss: () -> Unit,
    private val timetableRepository: TimetableRepository,
    private val analyticsRepository: AnalyticsRepository,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<LeonState<TimetableUiData>>(LeonState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        analyticsRepository.reportEvent(AnalyticsEvents.TIMETABLE_CLICK)
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _uiState.update { LeonState.Ready(createLoadingData()) }

            val result = timetableRepository.getTimetable()

            _uiState.update {
                val state = (it as? LeonState.Ready) ?: return@update it

                when (result) {
                    is Result.Success<TimetableModel> -> {
                        state.copy(
                            data = state.data.copy(
                                text = result.data.toText(),
                            ),
                        )
                    }

                    is Result.Failure -> {
                        state.copy(
                            data = state.data.copy(
                                text = result.reason.message,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: TimetableUiEvent) {
        when (event) {
            TimetableUiEvent.Dismiss -> dismiss()

            TimetableUiEvent.Copy -> analyticsRepository.reportEvent(AnalyticsEvents.COPY_TIMETABLE)
        }
    }

    private fun createLoadingData(): TimetableUiData =
        TimetableUiData(
            icon = FeatherIcons.Clock,
            title = Res.string.timetable_title,
            text = Res.string.timetable_loading,
            copyLabel = Res.string.timetable_copy,
            closeLabel = Res.string.timetable_close,
        )

    private fun TimetableModel.toText(): String {
        val shift1 = buildString {
            append(Res.string.timetable_first_shift)
            firstShift.time
                .toStringWithNumbers()
                .ifEmpty { Res.string.timetable_empty_shift }
                .also(::append)
        }
        val shift2 = buildString {
            append(Res.string.timetable_second_shift)
            secondShift.time
                .toStringWithNumbers()
                .ifEmpty { Res.string.timetable_empty_shift }
                .also(::append)
        }

        return "$shift1\n\n$shift2"
    }

    private fun List<String>.toStringWithNumbers(): String =
        mapIndexed { index, string -> "\n${index + 1}) $string" }.joinToString(separator = "")
}