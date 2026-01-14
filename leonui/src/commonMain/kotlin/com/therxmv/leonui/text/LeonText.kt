package com.therxmv.leonui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.therxmv.leonui.theme.LeonComponentPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun LeonText(
    text: String,
    modifier: Modifier = Modifier,
    size: LeonTextSize = LeonTextSize.Body1,
    color: Color = MaterialTheme.colorScheme.onSurface,
    weight: LeonTextWeight = LeonTextWeight.Normal,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = size.value,
            lineHeight = size.lineHeight,
            color = color,
            fontWeight = weight.value,
            textAlign = textAlign,
        ),
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Composable
fun LeonText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    size: LeonTextSize = LeonTextSize.Body1,
    color: Color = MaterialTheme.colorScheme.onSurface,
    weight: LeonTextWeight = LeonTextWeight.Normal,
    textAlign: TextAlign = TextAlign.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = size.value,
            lineHeight = size.lineHeight,
            color = color,
            fontWeight = weight.value,
            textAlign = textAlign,
        ),
        overflow = overflow,
        maxLines = maxLines,
    )
}

@Preview
@Composable
private fun LeonTextPreview(
    @PreviewParameter(LeonTextSizeProvider::class) size: LeonTextSize,
) {
    val text = "This is an example of $size text."
    LeonComponentPreview {
        LeonText(text = text, size = size, weight = LeonTextWeight.Normal)

        LeonText(text = text, size = size, weight = LeonTextWeight.Bold)
    }
}

private class LeonTextSizeProvider : PreviewParameterProvider<LeonTextSize> {
    override val values = sequenceOf(
        LeonTextSize.Title1,
        LeonTextSize.Title2,
        LeonTextSize.Body1,
        LeonTextSize.Body2,
    )
}