package com.therxmv.leonui.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.therxmv.datetime.picker.state.DatePickerState
import com.therxmv.datetime.picker.state.rememberDatePickerState
import com.therxmv.datetime.rememberWeekdayLabels
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonComponentPreview
import com.therxmv.leonui.theme.LeonTheme
import kotlinx.datetime.LocalDate

// TODO p4 add preview
@Composable
fun LeonMonthCalendar(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    onDayClick: (LocalDate) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LeonTheme.sizes.divider.thin),
    ) {
        GridRow {
            rememberWeekdayLabels().forEach {
                GridBox {
                    LeonText(
                        text = it,
                        weight = LeonTextWeight.Bold,
                    )
                }
            }
        }

        state.getMonthDays().forEach {
            GridRow {
                it.forEach { day ->
                    GridBox {
                        if (day.isCurrentMonth) {
                            MonthDay(
                                text = day.number,
                                withBackground = day.date == state.selectedDate,
                                withBorder = day.isToday,
                                onClick = {
                                    state.selectDate(day.date)
                                    onDayClick(day.date)
                                },
                            )
                        } else {
                            NotCurrentMonthDay(day.number)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthDay(
    text: String,
    withBackground: Boolean,
    withBorder: Boolean,
    onClick: () -> Unit,
) {
    val shape = LeonTheme.shapes.allRounded
    val color = LeonTheme.colors.primary
    val textColor = LeonTheme.colors.contentColorFor(color).takeIf { withBackground } ?: LeonTheme.colors.onSurface
    val textWeight = LeonTextWeight.Bold.takeIf { withBackground } ?: LeonTextWeight.Normal

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape)
            .clickable(onClick = onClick)
            .applyIf(withBackground) { background(color) }
            .applyIf(withBorder) { border(LeonTheme.sizes.border, color, shape) },
        contentAlignment = Alignment.Center,
    ) {
        LeonText(
            text = text,
            color = textColor,
            weight = textWeight,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun NotCurrentMonthDay(
    text: String,
) {
    LeonText(
        text = text,
        color = LeonTheme.colors.surface,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun GridRow(
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(LeonTheme.sizes.divider.thin),
        content = content,
    )
}

@Composable
private fun RowScope.GridBox(
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
        content = content,
    )
}

@Preview
@Composable
private fun LeonMonthCalendarPreview() {
    LeonComponentPreview {
        LeonMonthCalendar(
            state = rememberDatePickerState(),
            onDayClick = {},
        )
    }
}