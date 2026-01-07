package com.therxmv.napoleon.ui.dashboard.content.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.base.extensions.shimmerLoading

@Composable
fun SkeletonWidget(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f)
            .clip(LeonTheme.shapes.allRounded)
            .shimmerLoading(color),
    )
}