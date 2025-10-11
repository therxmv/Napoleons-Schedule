package com.therxmv.napoleon.base.extensions

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

inline fun Modifier.thenIf(
    predicate: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier =
    if (predicate) {
        this.modifier()
    } else {
        this
    }

// TODO improve
@Composable
fun Modifier.shimmerLoading(
    color: Color,
): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition()

    val translateAnimation by transition.animateFloat(
        initialValue = -100f,
        targetValue = size.width * 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
    )

    return onSizeChanged { size = it }.drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colorStops = arrayOf(
                    0f to color.copy(0.3f),
                    0.5f to color.copy(0.8f),
                    1f to color.copy(0.3f),
                ),
                start = Offset(x = translateAnimation - size.width / 2, y = translateAnimation - size.width / 2),
                end = Offset(x = translateAnimation * 1.1f, y = translateAnimation * 1.1f),
            )
        )
    }
}