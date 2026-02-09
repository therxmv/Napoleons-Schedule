package com.therxmv.leonui.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.therxmv.datetime.DateTimeConstants.Year
import com.therxmv.datetime.picker.state.DatePickerState
import com.therxmv.leonui.extensions.applyIf
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme

@Composable
fun LeonYearCalendar(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    onYearClick: (Int) -> Unit = {},
) {
    val lazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        state = lazyGridState,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(LeonTheme.sizes.divider.thin),
        verticalArrangement = Arrangement.spacedBy(LeonTheme.sizes.divider.thin),
    ) {
        items(
            items = Year.list,
            key = { it },
        ) { year ->
            Year(
                text = year.toString(),
                withBackground = year == state.selectedYear,
                withBorder = year == state.currentYear,
                onClick = {
                    state.selectYear(year)
                    onYearClick(year)
                },
            )
        }
    }

    LaunchedEffect(Unit) {
        // Kind of offset
        val index = (state.selectedYear - OFFSET_YEARS).coerceIn(Year.range)
        lazyGridState.scrollToItem(Year.list.indexOf(index))
    }
}

@Composable
private fun Year(
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
            .aspectRatio(2f)
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

private const val OFFSET_YEARS = 5