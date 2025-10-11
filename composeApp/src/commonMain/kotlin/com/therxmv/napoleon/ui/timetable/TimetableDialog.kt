package com.therxmv.napoleon.ui.timetable

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.ui.timetable.component.TimetableComponent
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.content.TimetableContent

@Composable
fun TimetableDialog(
    component: TimetableComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is BaseState.Ready<TimetableUiData> -> {
            TimetableContent(
                data = uiState.data,
                onEvent = component::onEvent,
            )
        }

        else -> Unit
    }
}