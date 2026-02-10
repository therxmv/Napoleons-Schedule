package com.therxmv.napoleon.ui.timetable.content

import androidx.compose.runtime.Composable
import com.therxmv.leonui.dialog.LeonDialog
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiEvent

@Composable
fun TimetableContent(
    data: TimetableUiData,
    onEvent: (TimetableUiEvent) -> Unit,
) {
    LeonDialog(
        icon = data.icon,
        title = data.title,
        cancelLabel = data.closeLabel,
        onCancel = { onEvent(TimetableUiEvent.Dismiss) },
        confirmLabel = data.copyLabel,
        onConfirm = { onEvent(TimetableUiEvent.Copy) },
        content = {
            LeonText(
                text = data.text,
                size = LeonTextSize.Body1,
            )
        }
    )
}

@LeonPreview
@Composable
private fun TimetableContentPreview() {
    LeonPreview {
        TimetableContent(
            data = PreviewMockData.timetableUiData,
            onEvent = {},
        )
    }
}