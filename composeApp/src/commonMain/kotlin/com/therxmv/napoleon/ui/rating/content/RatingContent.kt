package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        RatingInputs(
            modifier = Modifier.align(Alignment.TopCenter),
            data = data,
            onEvent = onEvent,
        )

        RatingResult(
            modifier = Modifier.align(Alignment.BottomCenter),
            data = data,
            onEvent = onEvent,
        )
    }
}