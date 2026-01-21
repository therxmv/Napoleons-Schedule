package com.therxmv.napoleon.ui.rating.content

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.therxmv.leonui.input.LeonTextInput
import com.therxmv.leonui.input.LeonTextInputStyle
import com.therxmv.leonui.list.LeonDividerType
import com.therxmv.leonui.list.LeonHorizontalDivider
import com.therxmv.leonui.text.LeonText
import com.therxmv.leonui.text.LeonTextSize
import com.therxmv.leonui.text.LeonTextWeight
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.leonui.theme.values.RoundedCornerShape
import com.therxmv.napoleon.ui.rating.component.RatingUiData
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent

@Composable
fun RatingResultAndProbability(
    modifier: Modifier = Modifier,
    data: RatingUiData,
    minFraction: Float,
    maxFraction: Float = 1f,
    threshold: Float = 0.2f,
    onEvent: (RatingUiEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var targetFraction by rememberSaveable { mutableFloatStateOf(minFraction) }
    var dragFraction by rememberSaveable { mutableFloatStateOf(minFraction) }
    val animatedFraction by animateFloatAsState(
        targetValue = dragFraction,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
    )

    // Surface consumes touch events and don't click on the button behind
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(animatedFraction)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = {
                        focusManager.clearFocus()
                        targetFraction = dragFraction
                    },
                    onDragEnd = {
                        dragFraction = when {
                            // When collapsed
                            targetFraction == minFraction && dragFraction > minFraction + threshold -> maxFraction
                            targetFraction == minFraction -> minFraction

                            // When expanded
                            targetFraction == maxFraction && dragFraction < maxFraction - threshold -> minFraction
                            else -> maxFraction
                        }
                    },
                    onVerticalDrag = { pointer, y ->
                        val maxHeight = size.height / dragFraction
                        val fractionDelta = -y / maxHeight
                        dragFraction = (dragFraction + fractionDelta).coerceIn(minFraction, maxFraction)

                        pointer.consume()
                    },
                )
            },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(top = LeonTheme.sizes.corner.largeRadius, bottom = 0.dp))
                .background(LeonTheme.colors.tertiary)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(LeonTheme.paddings.baseValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.base),
        ) {
            LeonHorizontalDivider(
                type = LeonDividerType.Small,
                color = LeonTheme.colors.onTertiary.copy(0.5f),
            )

            LeonText(
                text = data.ratingResult,
                size = LeonTextSize.Title1,
                color = LeonTheme.colors.onTertiary,
                weight = LeonTextWeight.Bold,
                textAlign = TextAlign.Center,
            )

            LeonText(
                text = data.probabilityResult,
                size = LeonTextSize.Title1,
                color = LeonTheme.colors.onTertiary,
                textAlign = TextAlign.Center,
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = dragFraction
                    },
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(LeonTheme.paddings.horizontal.base),
                verticalArrangement = Arrangement.spacedBy(LeonTheme.paddings.vertical.base),
            ) {
                itemsIndexed(
                    items = data.probabilityInputs,
                    span = { _, _ -> GridItemSpan(1) },
                    key = { index, item -> "$index-${item.title}" },
                ) { index, input ->
                    InputItem(
                        data = input,
                        onValueChange = {
                            onEvent(RatingUiEvent.UpdateProbabilityInput(input.title, it))
                        },
                        isLast = index == data.probabilityInputs.lastIndex,
                    )
                }
            }
        }
    }
}

@Composable
private fun InputItem(
    data: RatingUiData.ProbabilityInput,
    onValueChange: (String) -> Unit,
    isLast: Boolean,
) {
    Column {
        LeonText(
            modifier = Modifier.fillMaxWidth(),
            text = data.title,
            color = LeonTheme.colors.onTertiary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(LeonTheme.paddings.vertical.skinny))

        LeonTextInput(
            modifier = Modifier.fillMaxWidth(),
            value = data.value,
            error = data.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done.takeIf { isLast } ?: ImeAction.Next,
            ),
            onValueChange = onValueChange,
            style = LeonTextInputStyle.Tertiary,
        )

        if (data.error != null) {
            ErrorText(
                modifier = Modifier.fillMaxWidth(),
                error = data.error,
            )
        }
    }
}