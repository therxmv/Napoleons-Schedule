package com.therxmv.leonui.animation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.leonui.theme.LeonTheme

@Composable
fun Modifier.leonShimmerAnimation(
    color: Color,
): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition()

    val translateAnimation by transition.animateFloat(
        initialValue = START,
        targetValue = size.width * END_MULTIPLIER,
        animationSpec = infiniteRepeatable(
            animation = slowOutFastInAnimation,
            repeatMode = RepeatMode.Restart,
        ),
    )

    return onSizeChanged { size = it }.drawBehind {
        val colors = listOf(
            color.copy(MIN_ALPHA),
            color.copy(MAX_ALPHA),
        )
        val startXY = translateAnimation - size.width / 2
        val endXY = translateAnimation * END_MULTIPLIER

        drawRect(
            brush = Brush.linearGradient(
                colors = colors + colors.reversed(),
                start = Offset(x = startXY, y = startXY),
                end = Offset(x = endXY, y = endXY),
            )
        )
    }
}

private val slowOutFastInAnimation = tween<Float>(
    durationMillis = 2000,
    easing = CubicBezierEasing(0.3f, 0.3f, 1f, 0.2f),
)

private const val MIN_ALPHA = 0.3f
private const val MAX_ALPHA = 0.7f

private const val START = -100f
private const val END_MULTIPLIER = 1.1f

@LeonPreview
@Composable
private fun LeonShimmerAnimationPreview() {
    LeonPreview {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f)
                .clip(LeonTheme.shapes.allRounded)
                .leonShimmerAnimation(LeonTheme.colors.tertiary),
        )
    }
}