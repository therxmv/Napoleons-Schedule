package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.napoleon.ui.PreviewMockData
import com.therxmv.napoleon.ui.rating.component.RatingUiData
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RatingContent(
    modifier: Modifier = Modifier,
    data: RatingUiData,
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
            minFraction = resultsSheetHeight,
            onEvent = onEvent,
        )
    }
}

@Preview
@Composable
private fun RatingContentPreview() {
    LeonPreview {
        RatingContent(
            data = PreviewMockData.ratingUiData,
            onEvent = {},
        )
    }
}