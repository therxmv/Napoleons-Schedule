package com.therxmv.napoleon.ui.timetable.content

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.base.ui.CopyTextButton
import com.therxmv.napoleon.ui.timetable.component.TimetableUiData
import com.therxmv.napoleon.ui.timetable.component.TimetableUiEvent
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@Preview
@Composable
private fun TimetableContentPreview() {
    LeonPreview {
        TimetableContent(
            data = TimetableUiData(
                icon = FeatherIcons.Clock,
                title = Res.string.timetable_title,
                text = buildString {
                    append(Res.string.timetable_first_shift)
                    append("\n1) 8:00 - 9:20\n2) 9:35 - 10:55")
                    append("\n\n")
                    append(Res.string.timetable_second_shift)
                    append("\n")
                    append(Res.string.timetable_empty_shift)
                },
                copyLabel = Res.string.timetable_copy,
                closeLabel = Res.string.timetable_close,
            ),
            onEvent = {},
        )
    }
}