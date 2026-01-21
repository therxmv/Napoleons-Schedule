package com.therxmv.napoleon.ui.timetable.content

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyTextButton
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiEvent

@Composable
fun TimetableContent(
    data: TimetableUiData,
    onEvent: (TimetableUiEvent) -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(imageVector = data.icon, contentDescription = null)
        },
        iconContentColor = LeonTheme.colors.surfaceTint,
        title = {
            LeonText(
                text = data.title,
                size = LeonTextSize.Title1,
            )
        },
        text = {
            LeonText(
                text = data.text,
                size = LeonTextSize.Body1,
            )
        },
        onDismissRequest = {
            onEvent(TimetableUiEvent.Dismiss)
        },
        confirmButton = {
            CopyTextButton(
                label = data.copyLabel,
                textToCopy = data.text,
                onClick = { onEvent(TimetableUiEvent.Copy) },
            )
        },
        dismissButton = {
            LeonButton(
                style = LeonButtonStyle.Text,
                label = data.closeLabel,
                onClick = { onEvent(TimetableUiEvent.Dismiss) },
            )
        },
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