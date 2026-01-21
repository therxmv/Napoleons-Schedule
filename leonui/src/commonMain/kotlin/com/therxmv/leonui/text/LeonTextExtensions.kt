package com.therxmv.leonui.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString.Builder
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.withLink
import com.therxmv.leonui.theme.LeonTheme

fun LeonTextSize.toTextStyle(
    color: Color = Color.Unspecified,
    weight: LeonTextWeight = LeonTextWeight.Normal,
): TextStyle =
    TextStyle(
        fontSize = this.value,
        lineHeight = this.lineHeight,
        color = color,
        fontWeight = weight.value,
    )

@Composable
inline fun <R : Any> Builder.withLeonLink(
    url: String,
    size: LeonTextSize = LeonTextSize.Body1,
    color: Color = LeonTheme.colors.onSurface,
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