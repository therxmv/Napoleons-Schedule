package com.therxmv.leonui.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.theme.LeonComponentPreview

@Composable
fun LeonLoader(
    modifier: Modifier = Modifier.fillMaxSize(),
    size: Dp = 100.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
        )
    }
}

@Preview
@Composable
private fun LeonLoaderPreview() {
    LeonComponentPreview {
        LeonLoader()
    }
}