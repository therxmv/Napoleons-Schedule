package com.therxmv.leonui.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.Builder
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink

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

@Composable
inline fun <R : Any> Builder.withLeonLink(
    url: String,
    size: LeonTextSize = LeonTextSize.Body1,
    color: Color = MaterialTheme.colorScheme.onSurface,
    weight: LeonTextWeight = LeonTextWeight.Normal,
    block: Builder.() -> R,
): R =
    withLink(
        link = LinkAnnotation.Url(
            url = url,
            styles = TextLinkStyles(
                style = TextStyle(
                    fontSize = size.value,
                    lineHeight = size.lineHeight,
                    color = color,
                    fontWeight = weight.value,
                ).toSpanStyle()
            ),
        ),
        block = block,
    )