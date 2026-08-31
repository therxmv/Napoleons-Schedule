package com.therxmv.napoleon.ui.timetable.content

import androidx.compose.runtime.Composable
import com.therxmv.leonui.dialog.LeonDialog
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimetableContent(
    data: TimetableUiData,
    onEvent: (TimetableUiEvent) -> Unit,
) {
    LeonDialog(
        icon = data.icon,
        title = stringResource(data.titleRes),
        cancelLabel = stringResource(data.closeLabelRes),
        onCancel = { onEvent(TimetableUiEvent.Dismiss) },
        confirmLabel = stringResource(data.copyLabelRes),
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