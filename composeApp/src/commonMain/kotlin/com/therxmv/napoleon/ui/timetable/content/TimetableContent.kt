package com.therxmv.napoleon.ui.timetable.content

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.ui.CopyTextButton
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
        iconContentColor = LeonTheme.colors.dialogTint,
        title = {
            Text(text = data.title)
        },
        text = {
            Text(text = data.text)
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
            TextButton(
                colors = LeonTheme.colors.textButton,
                onClick = {
                    onEvent(TimetableUiEvent.Dismiss)
                },
            ) {
                Text(data.closeLabel)
            }
        },
    )
}