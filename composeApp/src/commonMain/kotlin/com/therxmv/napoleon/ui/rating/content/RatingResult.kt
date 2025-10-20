package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import com.therxmv.napoleon.ui.rating.component.RatingUiState
import com.therxmv.napoleon.ui.theme.NapoleonTheme

@Composable
fun RatingResult(
    modifier: Modifier = Modifier,
    data: RatingUiState,
    onEvent: (RatingUiEvent) -> Unit,
) {
    val windowInsets = WindowInsets.navigationBars.union(WindowInsets.ime)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(NapoleonTheme.shapes.onlyTopRounded)
            .background(MaterialTheme.colorScheme.tertiary)
            .windowInsetsPadding(windowInsets)
            .padding(NapoleonTheme.paddings.defaultValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResultText(data.result)
    }
}

@Composable
private fun ResultText(
    result: String,
) {
    Text(
        text = result,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onTertiary,
    )
}