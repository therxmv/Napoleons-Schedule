package com.therxmv.napoleon.ui.timetable

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.therxmv.leonui.state.LeonState
import com.therxmv.napoleon.ui.timetable.component.TimetableComponent
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.content.TimetableContent

@Composable
fun TimetableDialog(
    component: TimetableComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle().value

    when (uiState) {
        is LeonState.Ready<TimetableUiData> -> {
            TimetableContent(
                data = uiState.data,
                onEvent = component::onEvent,
            )
        }

        else -> Unit
    }
}