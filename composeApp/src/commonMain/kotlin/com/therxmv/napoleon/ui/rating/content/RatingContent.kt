package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import com.therxmv.napoleon.ui.rating.component.RatingUiState

@Composable
fun RatingContent(
    modifier: Modifier = Modifier,
    data: RatingUiState,
    onEvent: (RatingUiEvent) -> Unit,
) {
    val resultsSheetHeight = remember { 0.2f }
    val inputsHeight = remember { 1 - resultsSheetHeight }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        RatingInputs(
            modifier = Modifier.align(Alignment.TopCenter),
            data = data,
            heightFraction = inputsHeight,
            onEvent = onEvent,
        )

        RatingResult(
            modifier = Modifier.align(Alignment.BottomCenter),
            data = data,
            heightFraction = resultsSheetHeight,
            onEvent = onEvent,
        )
    }
}