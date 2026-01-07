package com.therxmv.napoleon.ui.rating.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.therxmv.leonui.theme.LeonPreview
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.ui.rating.component.RatingUiEvent
import com.therxmv.napoleon.ui.rating.component.RatingUiState
import com.therxmv.napoleon.ui.rating.component.RatingUiState.ProbabilityInput
import com.therxmv.napoleon.ui.rating.component.RatingUiState.SubjectInput
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            data = RatingUiState(
                nameLabel = Res.string.rating_name_label,
                creditsLabel = Res.string.rating_credits_label,
                scoreLabel = Res.string.rating_score_label,
                addInputLabel = Res.string.rating_add_label,
                subjectInputs = listOf(SubjectInput(), SubjectInput()),
                ratingResult = "${Res.string.rating_label} 88.14",
                probabilityInputs = listOf(
                    ProbabilityInput(
                        title = ProbabilityInput.Id.Capacity.title,
                        value = "20",
                    ),
                    ProbabilityInput(
                        title = ProbabilityInput.Id.Quota.title,
                        value = "8",
                    ),
                    ProbabilityInput(
                        title = ProbabilityInput.Id.Average.title,
                        value = "75",
                    ),
                    ProbabilityInput(
                        title = ProbabilityInput.Id.Deviation.title,
                        value = "5",
                    ),
                ),
                probabilityResult = "${Res.string.rating_probability} 78.4%",
                infoData = RatingUiState.Info(
                    text = Res.string.rating_info_text,
                    link = "link",
                    linkText = Res.string.rating_info_link_text,
                ),
            ),
            onEvent = {},
        )
    }
}