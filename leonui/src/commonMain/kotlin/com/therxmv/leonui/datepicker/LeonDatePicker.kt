package com.therxmv.leonui.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.datetime.picker.state.DatePickerState
import com.therxmv.datetime.picker.state.rememberDatePickerState
import com.therxmv.leonui.button.LeonButton
import com.therxmv.leonui.button.LeonIconButton
import com.therxmv.leonui.button.LeonIconButtonStyle
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.ChevronDown
import kotlinx.datetime.LocalDate

@Composable
fun LeonDatePicker(
    state: DatePickerState,
    onDateSelected: (LocalDate) -> Unit,
) {
    LeonDatePickerColumn {
        LeonDatePickerContent(state, onDateSelected)
    }
}

@Composable
internal fun LeonDatePickerContent(
    state: DatePickerState,
    onDateSelected: (LocalDate) -> Unit,
) {
    Header()
    Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.baggy))

    CalendarActions(state = state)
    Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.skinny))

    LeonMonthCalendar(state = state, onDayClick = onDateSelected)
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .aspectRatio(1f),
            imageVector = FeatherIcons.Calendar,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LeonTheme.paddings.horizontal.base))

        Column {
            LeonText(
                text = "Selected date", // TODO translate string
            )

            LeonText(
                text = "January 12, 2026", // TODO translate string
                size = LeonTextSize.Title1,
                weight = LeonTextWeight.Bold,
            )
        }
    }
}

@Composable
private fun CalendarActions(
    state: DatePickerState,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LeonButton(
            label = "2026", // TODO translate string
            textPadding = PaddingValues(
                horizontal = LeonTheme.paddings.horizontal.skinny,
            ),
            suffixIcon = FeatherIcons.ChevronDown,
            onClick = {}, // TODO show year grid
        )

        Row {
            LeonIconButton(
                icon = FeatherIcons.ArrowLeft,
                style = LeonIconButtonStyle.Filled(),
                onClick = state::minusMonth,
            )
            LeonIconButton(
                icon = FeatherIcons.ArrowRight,
                style = LeonIconButtonStyle.Filled(),
                onClick = state::plusMonth,
            )
        }
    }
}

@Composable
internal fun LeonDatePickerColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(LeonTheme.shapes.allRounded)
            .background(LeonTheme.colors.surface)
            .padding(
                vertical = LeonTheme.paddings.vertical.baggy,
                horizontal = LeonTheme.paddings.horizontal.base,
            ),
        content = content,
    )
}

@Preview
@Composable
private fun LeonMonthCalendarPreview() {
    LeonComponentPreview {
        LeonDatePicker(
            state = rememberDatePickerState(),
            onDateSelected = {},
        )
    }
}