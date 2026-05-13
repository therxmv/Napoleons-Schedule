package com.therxmv.leonui.datepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.therxmv.datetime.picker.state.DatePickerState
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonButtonStyle
import com.therxmv.leonui.theme.LeonTheme
import kotlinx.datetime.LocalDate

// TODO p4 add preview
@Composable
fun LeonDatePickerDialog(
    state: DatePickerState,
    onDateSelected: (LocalDate) -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(onDismissRequest = onCancel) {
        LeonDatePickerColumn(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            LeonDatePickerContent(state)
            Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.base))

            DialogActions(
                modifier = Modifier.align(Alignment.End),
                onConfirm = { onDateSelected(state.selectedDate) },
                onCancel = onCancel,
            )
        }
    }
}

@Composable
private fun DialogActions(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Row(
        modifier = modifier,
    ) {
        LeonButton(
            label = "Cancel", // TODO p1 translate string
            style = LeonButtonStyle.Text(),
            onClick = onCancel,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.skinny))

        LeonButton(
            label = "Confirm", // TODO p1 translate string
            style = LeonButtonStyle.Text(),
            onClick = onConfirm,
        )
    }
}