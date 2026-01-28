package com.therxmv.leonui.animation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.ui.Modifier

context(scope: LazyItemScope)
fun Modifier.leonLazyListAnimation(): Modifier =
    with(scope) {
        animateItem(
            placementSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                dampingRatio = Spring.DampingRatioLowBouncy,
            ),
            fadeInSpec = null,
            fadeOutSpec = null,
        )
    }

context(scope: LazyGridItemScope)
fun Modifier.leonLazyGridAnimation(): Modifier =
    with(scope) {
        animateItem(
            placementSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                dampingRatio = Spring.DampingRatioLowBouncy,
            ),
            fadeInSpec = null,
            fadeOutSpec = null,
        )
    }